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

import lambdasinaction.chap10.dsl.model.Order;
import lambdasinaction.chap10.dsl.model.Stock;
import lambdasinaction.chap10.dsl.model.Trade;

/**
 * 第一种DSL设计方法---方法链构
 */
public class MethodChainingOrderBuilder {

  public final Order order = new Order(); //封装的订单对象

  private MethodChainingOrderBuilder(String customer) {
    order.setCustomer(customer);
  }

  //静态工厂方法，用于创建指定客户订单的构建器
  public static MethodChainingOrderBuilder forCustomer(String customer) {
    return new MethodChainingOrderBuilder(customer);
  }

  public Order end() { //终止创建订单，并返回订单
    return order;
  }

  public TradeBuilder buy(int quantity) { //创建一个TradeBuilder，构造一个购买股票的交易
    return new TradeBuilder(this, Trade.Type.BUY, quantity);
  }

  public TradeBuilder sell(int quantity) { //创建一个TradeBuilder，构造一个卖出股票的交易
    return new TradeBuilder(this, Trade.Type.SELL, quantity);
  }

  private MethodChainingOrderBuilder addTrade(Trade trade) {
    order.addTrade(trade); //向订单中添加交易
    return this;
  }

  public static class TradeBuilder {

    private final MethodChainingOrderBuilder builder;
    public final Trade trade = new Trade();

    /**
     * 构建交易的构建器
     * @param builder
     * @param type 交易类型
     * @param quantity 交易数量
     */
    private TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
      this.builder = builder;
      trade.setType(type);
      trade.setQuantity(quantity);
    }

    //创建股票的构建器
    public StockBuilder stock(String symbol) {
      return new StockBuilder(builder, trade, symbol);
    }

  }

  public static class TradeBuilderWithStock {

    private final MethodChainingOrderBuilder builder;
    private final Trade trade;

    public TradeBuilderWithStock(MethodChainingOrderBuilder builder, Trade trade) {
      this.builder = builder;
      this.trade = trade;
    }

    //设置交易股票的单位价格
    public MethodChainingOrderBuilder at(double price) {
      trade.setPrice(price);
      return builder.addTrade(trade);
    }

  }

  public static class StockBuilder {

    private final MethodChainingOrderBuilder builder;
    private final Trade trade;
    private final Stock stock = new Stock();

    private StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol) {
      this.builder = builder;
      this.trade = trade;
      stock.setSymbol(symbol);
    }

    //这个on方法负责设定股票市场，将股票添加到交易中
    public TradeBuilderWithStock on(String market) {
      stock.setMarket(market);
      trade.setStock(stock);
      return new TradeBuilderWithStock(builder, trade);
    }

  }

}
