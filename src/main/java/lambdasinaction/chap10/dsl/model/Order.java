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

package lambdasinaction.chap10.dsl.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order {

  private String customer; //客户
  private List<Trade> trades = new ArrayList<>(); //订单里的多笔交易

  public void addTrade( Trade trade ) {
    trades.add( trade );
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer( String customer ) {
    this.customer = customer;
  }

  public double getValue() { //将每笔对应的交易映射成，每笔交易的总金额，并求和
    return trades.stream().mapToDouble( Trade::getValue ).sum();
  }

  @Override
  public String toString() {
    String strTrades = trades.stream().map(t -> "  " + t).collect(Collectors.joining("\n", "[\n", "\n]"));
    return String.format("Order[customer=%s, trades=%s]", customer, strTrades);
  }

}
