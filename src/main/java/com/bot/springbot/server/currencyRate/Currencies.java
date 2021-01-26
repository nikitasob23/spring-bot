package com.bot.springbot.server.currencyRate;

import com.bot.springbot.server.ServerConfig;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@JsonIgnoreProperties({"logger", "sourceFileName", "sourceUrl", "requestTime", "russianCodeName",
        "russianCurrencyCode", "russianCurrencyTempName"})
public class Currencies {

    private final Logger logger = LoggerFactory.getLogger(Currencies.class);

    private final String sourceFileName;

    private final String sourceUrl;

    private final long requestTimeMillis;

    private final String russianCurrencyCode = "RUB";

    private final String russianCurrencyTempName = "РОС РУБ";

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

    public Currencies() {
        this.sourceFileName = null;
        this.sourceUrl = null;
        this.requestTimeMillis = 0;
    }

    public Currencies(ServerConfig config, long requestTime) {
        this.sourceFileName = config.getCurrenciesFileName();
        this.sourceUrl = config.getCurrenciesSourceUrl();
        this.requestTimeMillis = requestTime;
    }

    public Currencies(CurrenciesBuilder builder) {
        this.sourceFileName = builder.getSourceFileName();
        this.sourceUrl = builder.getSourceUrl();
        this.requestTimeMillis = builder.getRequestTimeMillis();
        this.date = builder.getDate();
        this.previousDate = builder.getPreviousDate();
        this.previousUrl = builder.getPreviousUrl();
        this.timestamp = builder.getTimestamp();
        this.valute = builder.getValute();
    }

    public Logger getLogger() {
        return logger;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public long getRequestTimeMillis() {
        return requestTimeMillis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPreviousDate() {
        return previousDate;
    }

    public void setPreviousDate(String previousDate) {
        this.previousDate = previousDate;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Currency> getValute() {
        return valute;
    }

    public void setValute(Map<String, Currency> valute) {
        this.valute = valute;
    }

    @Override
    public String toString() {
        return "Currencies{" +
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

    public Currencies compareRequestTimeAndUpdate(RestTemplate template, long currentTimeMillis, long diffMillis) {
        if (currentTimeMillis - requestTimeMillis < diffMillis) {
            return this;
        }
        return new CurrenciesBuilder()
                .setSourceFileName(sourceFileName)
                .setSourceUrl(sourceUrl)
                .setRequestTimeMillis(currentTimeMillis)
                .update(template);
    }

    public Currency findCurrencyByTempName(String[] tempNameSplit) {
        for (Currency currency : valute.values()) {
            boolean completeMatch = true;
            String[] nameSplit = currency.getName().toLowerCase().split(" ");
            for (int i = 0; i < nameSplit.length; i++) {
                if (!nameSplit[i].contains(tempNameSplit[i])) {
                    completeMatch = false;
                    break;
                }
            }
            if (completeMatch) {
                return currency;
            }
        }
        return null;
    }

    private double parseToRubValue(double value, String id) {
        Currency currency = getCurrency(id);
        return value / currency.getNominal() * currency.getValue();
    }

    private double parseToAnotherValue(double value, String id) {
        Currency currency = getCurrency(id);
        return value * currency.getNominal() / currency.getValue();
    }

    public double parseValue(double value, String id, String anotherId) {
        double rubValue = id.equals(russianCurrencyCode)
                || id.contains(russianCurrencyTempName) ? value : parseToRubValue(value, id);

        return anotherId.equals(russianCurrencyCode)
                || anotherId.contains(russianCurrencyTempName) ? rubValue : parseToAnotherValue(rubValue, anotherId);
    }

    public Currency getCurrency(String id) {
        if (id.equals(russianCurrencyCode) || id.contains(russianCurrencyTempName)) {
            final String russianCurrencyName = "Российский рубль";
            Currency currency = new Currency();
            currency.setName(russianCurrencyName);
            return currency;
        }
        Currency currency = valute.get(id);
        if (currency == null) {
            currency = findCurrencyByTempName(getTempName(id));
        }
        return currency;
    }

    private String[] getTempName(String id) {
        String[] splitTempName = id.toLowerCase().split(" ");
        for (int i = 0; i < splitTempName.length; i++) {
            splitTempName[i] = splitTempName[i].substring(0, splitTempName[i].length() - 2);
        }
        return splitTempName;
    }
}