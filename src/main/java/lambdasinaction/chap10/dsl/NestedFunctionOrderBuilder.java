/*
 * Copyright 2005 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lambdasinaction.chap10.dsl;

import java.util.stream.Stream;

import lambdasinaction.chap10.dsl.model.Order;
import lambdasinaction.chap10.dsl.model.Stock;
import lambdasinaction.chap10.dsl.model.Trade;

/**
 * 第二种DSL设计方法---嵌套函数
 */
public class NestedFunctionOrderBuilder {

  /**
   *
   * @param customer 指定的客户名称
   * @param trades 多笔交易
   * @return
   */
  public static Order order(String customer, Trade... trades) {
    Order order = new Order();
    order.setCustomer(customer);
    Stream.of(trades).forEach(order::addTrade); //将所有交易添加到订单中
    return order;
  }

  //创建一个买入股票的交易
  public static Trade buy(int quantity, Stock stock, double price) {
    return buildTrade(quantity, stock, price, Trade.Type.BUY);
  }

  //创建一个卖出股票的交易
  public static Trade sell(int quantity, Stock stock, double price) {
    return buildTrade(quantity, stock, price, Trade.Type.SELL);
  }

  private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type buy) {
    Trade trade = new Trade();
    trade.setQuantity(quantity);
    trade.setType(buy);
    trade.setStock(stock);
    trade.setPrice(price);
    return trade;
  }

  public static double at(double price) {
    return price;
  }

  public static Stock stock(String symbol, String market) {
    Stock stock = new Stock();
    stock.setSymbol(symbol);
    stock.setMarket(market);
    return stock;
  }

  public static String on(String market) {
    return market;
  }

}
