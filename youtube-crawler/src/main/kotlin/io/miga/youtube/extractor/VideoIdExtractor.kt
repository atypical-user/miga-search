package io.miga.youtube.extractor

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import org.jsoup.Jsoup
import org.mozilla.javascript.Context
import org.slf4j.LoggerFactory

class VideoIdExtractor {

    private val log = LoggerFactory.getLogger(VideoIdExtractor::class.java)

    fun extractVideoIdsFrom(html: String): List<String> {
        val document = Jsoup.parse(html)
        val scriptElements = document.head().getElementsByTag("script")

        log.debug("Found {} script elements", scriptElements.size)
        val result = ArrayList<String>()
        for (element in scriptElements) {
            val data = element.html()
            if (data.contains("ytInitialData")) {
                val jsonObject = JsonParser.parseString(getVariableContent(data)).asJsonObject
                handleObject(jsonObject, result)
            }
        }
        log.debug("Found {} video ids", result.size)
        return result
    }

    private fun getVariableContent(jsCode: String): String {
        // Enter the Rhino context
        val cx = Context.enter()
        var result = ""
        try {
            // Create a new scope (like the global object)
            val scope = cx.initStandardObjects()

            // Define and run JavaScript code
            cx.evaluateString(scope, jsCode, "script", 1, null)

            // Convert them to Java types if needed
            val jsonString = cx.evaluateString(scope, "JSON.stringify(ytInitialData)", "script", 1, null)
            log.debug("Serialized object: {}", Context.toString(jsonString))
            result = Context.toString(jsonString)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        finally {
            Context.exit()
        }
        return result
    }

    private fun handleObject(jsonObject: JsonObject, result: ArrayList<String>) {
        jsonObject.keySet().forEach {
            val value = jsonObject.get(it)
            if (value.isJsonPrimitive) {
                handlePrimitive(it, value.asJsonPrimitive, result)
            } else if (value.isJsonObject) {
                handleObject(value.asJsonObject, result)
            } else if (it == "videoIds" && value.isJsonArray)  {
                handleIdArray(value.asJsonArray, result)
            } else if (value.isJsonArray) {
                handleArray(value.asJsonArray, result)
            }
        }
    }

    private fun handleArray(jsonArray: JsonArray, result: ArrayList<String>) {
        jsonArray.forEach {
            if (it.isJsonObject) {
                handleObject(it.asJsonObject, result)
            } else if (it.isJsonArray) {
                handleArray(it.asJsonArray, result)
            }
        }

    }

    private fun handleIdArray(jsonArray: JsonArray, result: ArrayList<String>) {
        jsonArray.forEach {
            if (!result.contains(it.asString)) {
                result.add(it.asString)
            }
        }
    }

    private fun handlePrimitive(
        key: String,
        jsonPrimitive: JsonPrimitive,
        result: ArrayList<String>
    ) {
        if ("videoId" == key) {
            if (!result.contains(jsonPrimitive.asString)) {
                result.add(jsonPrimitive.asString)
            }
        }
    }
}