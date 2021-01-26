package com.bot.springbot.server.currencyRate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@JsonIgnoreProperties({"logger"})
public class CurrenciesBuilder {

    private Logger logger = LoggerFactory.getLogger(CurrenciesBuilder.class);

    private String sourceFileName;

    private String sourceUrl;

    private long requestTimeMillis;

    @JsonProperty("Date")
    private String date;

    @JsonProperty("PreviousDate")
    private String previousDate;

    @JsonProperty("PreviousURL")
    private String previousUrl;

    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("Valute")
    private Map<String, Currency> valute;

    public CurrenciesBuilder() {
    }

    public Logger getLogger() {
        return logger;
    }

    public CurrenciesBuilder setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public CurrenciesBuilder setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
        return this;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public CurrenciesBuilder setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        return this;
    }

    public long getRequestTimeMillis() {
        return requestTimeMillis;
    }

    public CurrenciesBuilder setRequestTimeMillis(long requestTimeMillis) {
        this.requestTimeMillis = requestTimeMillis;
        return this;
    }

    public String getDate() {
        return date;
    }

    public CurrenciesBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    public String getPreviousDate() {
        return previousDate;
    }

    public CurrenciesBuilder setPreviousDate(String previousDate) {
        this.previousDate = previousDate;
        return this;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public CurrenciesBuilder setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public CurrenciesBuilder setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Map<String, Currency> getValute() {
        return valute;
    }

    public CurrenciesBuilder setValute(Map<String, Currency> valute) {
        this.valute = valute;
        return this;
    }

    @Override
    public String toString() {
        return "CurrenciesBuilder{" +
                "sourceFileName='" + sourceFileName + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", requestTimeMillis=" + requestTimeMillis +
                ", date='" + date + '\'' +
                ", previousDate='" + previousDate + '\'' +
                ", previousUrl='" + previousUrl + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", valute=" + valute +
                '}';
    }

    public Currencies build(RestTemplate template) {
        Path sourceFilePath = Paths.get(sourceFileName);
        if (Files.exists(sourceFilePath)) {
            return getCurrenciesFromSourceFile(sourceFilePath);
        }
        logger.info("Source file with currencies {} was not found", sourceFileName);
        return update(template);
    }

    public Currencies update(RestTemplate template) {
        String response = template.getForObject(
                sourceUrl,
                String.class
        );

        CurrenciesBuilder builder;
        try {
            builder = new ObjectMapper().readValue(response, CurrenciesBuilder.class)
                    .setSourceFileName(sourceFileName)
                    .setSourceUrl(sourceUrl)
                    .setRequestTimeMillis(requestTimeMillis);
            logger.info("Successful receipt of currencies from a source url {}. Got object {}", sourceUrl, builder);
        } catch (IOException e) {
            logger.info("Failed receipt of currencies from a source url {}. Exception message {}", sourceUrl, e.getMessage());
            return null;
        }
        writeInSourceFile(builder);

        return new Currencies(builder);
    }

    public void writeInSourceFile(CurrenciesBuilder builder) {
        Path sourceFilePath = Paths.get(sourceFileName);
        try (BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(sourceFilePath))) {
            new ObjectMapper().writeValue(writer, builder);
            logger.info("Successful saving of currencies in the " + sourceFileName);
        } catch (IOException e) {
            logger.info("Failed saving currencies in the {}. Exception message {}", sourceFileName, e.getMessage());
        }
    }

    private Currencies getCurrenciesFromSourceFile(Path sourceFilePath) {
        CurrenciesBuilder builder;
        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(sourceFilePath))) {
            builder = new ObjectMapper().readValue(reader, CurrenciesBuilder.class)
                    .setSourceFileName(sourceFileName)
                    .setSourceUrl(sourceUrl);
            logger.info("Successful reading of currencies from a file {}. Got currencies {}", sourceFilePath.toString(), builder);
        } catch (IOException e) {
            logger.info("Failed reading of currencies from a file {}. Exception message {}", sourceFilePath.toString(), e.getMessage());
            return null;
        }

        return new Currencies(builder);
    }
}
