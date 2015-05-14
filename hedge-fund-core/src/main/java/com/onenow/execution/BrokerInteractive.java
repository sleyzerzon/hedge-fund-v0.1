package com.onenow.execution;

import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub, will be fixed later
 */
public class BrokerInteractive implements Broker, BrokerController.ConnectionHandler {

  @Override
  public List<Underlying> getUnderlying() {
    return null;
  }

  @Override
  public Portfolio getMarketPortfolio() {
    return null;
  }

  @Override
  public Portfolio getMyPortfolio() {
    return null;
  }

  @Override
  public Double getPrice(Investment inv, TradeType type) {
    return null;
  }

  @Override
  public List<Trade> getTrades() {
    return null;
  }

  @Override
  public void enterTransaction(Transaction trans) {

  }

  @Override
  public void readHistoricalQuotes(Investment inv, String end, QuoteHistory history) {

  }

  @Override
  public void connected() {

  }

  @Override
  public void disconnected() {

  }

  @Override
  public void accountList(ArrayList<String> list) {

  }

  @Override
  public void error(Exception e) {

  }

  @Override
  public void message(int id, int errorCode, String errorMsg) {

  }

  @Override
  public void show(String string) {

  }
}
