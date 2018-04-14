package uj.jwzp.w2.parser;

import org.javatuples.Pair;

import java.text.SimpleDateFormat;

public class ProgramParameters {
    Pair<Integer, Integer> customerIdsRange;
    Pair<String, String> dateRange;
    String itemsFileName;
    Pair<Integer, Integer> itemsCountRange;
    Pair<Integer, Integer> itemsQuantityRange;
    Integer eventsCount;
    String outDir;

    public ProgramParameters(Pair<Integer, Integer> customerIdsRange, Pair<String, String> dateRange,
                             String itemsFileName, Pair<Integer, Integer> itemsCountRange,
                             Pair<Integer, Integer> itemsQuantityRange, Integer eventsCount, String outDir) {
        this.customerIdsRange = customerIdsRange;
        this.dateRange = dateRange;
        this.itemsFileName = itemsFileName;
        this.itemsCountRange = itemsCountRange;
        this.itemsQuantityRange = itemsQuantityRange;
        this.eventsCount = eventsCount;
        this.outDir = outDir;
    }

    public Pair<Integer, Integer> getCustomerIdsRange() {
        return customerIdsRange;
    }

    public Pair<String, String> getDateRange() {
        return dateRange;
    }

    public String getItemsFileName() {
        return itemsFileName;
    }

    public Pair<Integer, Integer> getItemsCountRange() {
        return itemsCountRange;
    }

    public Pair<Integer, Integer> getItemsQuantityRange() {
        return itemsQuantityRange;
    }

    public Integer getEventsCount() {
        return eventsCount;
    }

    public String getOutDir() {
        return outDir;
    }

    @Override
    public String toString() {
        return "ProgramParameters{" +
                "customerIdsRange=" + customerIdsRange +
                ", dateRange=" + dateRange +
                ", itemsFileName='" + itemsFileName + '\'' +
                ", itemsCountRange=" + itemsCountRange +
                ", itemsQuantityRange=" + itemsQuantityRange +
                ", eventsCount=" + eventsCount +
                ", outDir='" + outDir + '\'' +
                '}';
    }
}
