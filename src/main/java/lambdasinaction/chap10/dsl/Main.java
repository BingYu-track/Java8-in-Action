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
 *
 */
public class Main {

  public static void main(String[] args) {
    Main main = new Main();
    main.plain();
    main.methodChaining();
//    main.nestedFunction();
//    main.lambda();
//    main.mixed();
  }

  //命令式
  public void plain() {
    Order order = new Order();
    order.setCustomer("BigBank");

    Trade trade1 = new Trade();
    trade1.setType(Trade.Type.BUY);

    Stock stock1 = new Stock();
    stock1.setSymbol("IBM");
    stock1.setMarket("NYSE");

    trade1.setStock(stock1);
    trade1.setPrice(125.00);
    trade1.setQuantity(80);
    order.addTrade(trade1);

    Trade trade2 = new Trade();
    trade2.setType(Trade.Type.BUY);

    Stock stock2 = new Stock();
    stock2.setSymbol("GOOGLE");
    stock2.setMarket("NASDAQ");

    trade2.setStock(stock2);
    trade2.setPrice(375.00);
    trade2.setQuantity(50);
    order.addTrade(trade2);

    System.out.println("Plain:");
    System.out.println(order);
  }

  //方法链接DSL
  public void methodChaining() {
                                              //创建了指定客户的订单构建器
    Order order = MethodChainingOrderBuilder.forCustomer("BigBank")
        //创建了一个交易构建器，且购买数量为80
        .buy(80)
        //创建了一个股票构建器，且股票是IBM的，在NYSE上市的
        .stock("IBM")
            .on("NYSE")
        //设置了单位交易价格且返回了订单构建器
        .at(125.00)
        .sell(50)
        .stock("GOOGLE")
            .on("NASDAQ")
        .at(375.00)
        .end();  //最终创建了订单order

    System.out.println("Method chaining:");
    System.out.println(order);
  }

  //嵌套函数DSL
  public void nestedFunction() {
    Order order = NestedFunctionOrderBuilder.order("BigBank",
        //一个够买了数量为80的,在NYSE上市的IBM股票，每笔股票单位价格是125的交易
        NestedFunctionOrderBuilder.buy(80,
            NestedFunctionOrderBuilder.stock("IBM", NestedFunctionOrderBuilder.on("NYSE")),
            NestedFunctionOrderBuilder.at(125.00)
        ),

        //一个卖出了数量为50，在NASDAQ市场上的GOOGLE股票，且卖出的单位价格为375
        NestedFunctionOrderBuilder.sell(50,
            NestedFunctionOrderBuilder.stock("GOOGLE", NestedFunctionOrderBuilder.on("NASDAQ")),
            NestedFunctionOrderBuilder.at(375.00)
        )
    );

    System.out.println("Nested function:");
    System.out.println(order);
  }

  //Lambda表达式定义的函数序列
  public void lambda() {
    Order order = LambdaOrderBuilder.order( o -> { //这里的o就是LambdaOrderBuilder对象
      o.forCustomer( "BigBank" ); //订单所属客户

      //购买股票
      o.buy( t -> { //这里的t是TradeBuilder对象
        t.quantity(80);
        t.price(125.00);
        t.stock(s -> {
          s.symbol("IBM");
          s.market("NYSE");
        });
      });

      //卖出股票
      o.sell( t -> {
        t.quantity(50);
        t.price(375.00);
        t.stock(s -> {
          s.symbol("GOOGLE");
          s.market("NASDAQ");
        });
      });
    });

    System.out.println("Lambda:");
    System.out.println(order);
  }

  //使用混合的风格
  public void mixed() {
    Order order =
        MixedBuilder.forCustomer("BigBank",  //顶层使用了"嵌套函数"的设计模式
            //参数2
            MixedBuilder.buy(t -> t.quantity(80)  //这里创建单个交易使用了"lambda表达式"
                .stock("IBM")  //从这里开始使用了"方法链接的模式"
                .on("NYSE")
                .at(125.00)),

            //参数3
            MixedBuilder.sell(t -> t.quantity(50)
                .stock("GOOGLE")
                .on("NASDAQ")
                .at(375.00))
        );

    System.out.println("Mixed:");
    System.out.println(order);
  }

}
