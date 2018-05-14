package uj.jwzp.w2.service;

import uj.jwzp.w2.entity.Transaction;
import uj.jwzp.w2.parser.ProgramParameters;

import java.text.ParseException;
import java.util.List;

public interface ITransactionWriter {
    void writeToFile(List<Transaction> transactionList, String outDirectory) throws ParseException;
}
