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

import java.util.function.Consumer;

import lambdasinaction.chap10.dsl.model.Order;
import lambdasinaction.chap10.dsl.model.Stock;
import lambdasinaction.chap10.dsl.model.Trade;

/**
 * 第三种DSL设计方法---Lambda表达式定义的函数序列
 */
public class LambdaOrderBuilder {

  private Order order = new Order();

  public static Order order(Consumer<LambdaOrderBuilder> consumer) {
    LambdaOrderBuilder builder = new LambdaOrderBuilder();
    consumer.accept(builder); //这个方法被传过来的lambda表达式实现，具体实现会调用下面的forCustomer()、buy()、sell()等方法
    return builder.order;
  }

  public void forCustomer(String customer) {
    order.setCustomer(customer);
  }

  public void buy(Consumer<TradeBuilder> consumer) {
    trade(consumer, Trade.Type.BUY);
  }

  public void sell(Consumer<TradeBuilder> consumer) {
    trade(consumer, Trade.Type.SELL);
  }

  private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
    TradeBuilder builder = new TradeBuilder();
    builder.trade.setType(type); //设置交易类型
    consumer.accept(builder); //这里传递的lambda表达式的accepr具体实现会调用TradeBuilder的quantity()、price()和stock()方法
    order.addTrade(builder.trade); //订单里增加交易
  }

  public static class TradeBuilder {

    private Trade trade = new Trade();

    public void quantity(int quantity) {
      trade.setQuantity(quantity);
    }

    public void price(double price) {
      trade.setPrice(price);
    }

    public void stock(Consumer<StockBuilder> consumer) {
      StockBuilder builder = new StockBuilder();
      consumer.accept(builder);
      trade.setStock(builder.stock);
    }

  }

  public static class StockBuilder {

    private Stock stock = new Stock();

    public void symbol(String symbol) {
      stock.setSymbol(symbol);
    }

    public void market(String market) {
      stock.setMarket(market);
    }

  }

}
