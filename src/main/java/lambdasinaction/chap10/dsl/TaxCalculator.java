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

import java.util.function.DoubleUnaryOperator;

import lambdasinaction.chap10.dsl.model.Order;
import lambdasinaction.chap10.dsl.model.Tax;

/**
 * 使用方法引用实现税率计算器
 */
public class TaxCalculator {

  public static double calculate(Order order, boolean useRegional, boolean useGeneral, boolean useSurcharge) {
    double value = order.getValue();
    if (useRegional) {
      value = Tax.regional(value);
    }
    if (useGeneral) {
      value = Tax.general(value);
    }
    if (useSurcharge) {
      value = Tax.surcharge(value);
    }
    return value;
  }

  private boolean useRegional;
  private boolean useGeneral;
  private boolean useSurcharge;

  public TaxCalculator withTaxRegional() { //地区税
    useRegional = true;
    return this;
  }

  public TaxCalculator withTaxGeneral() { //
    useGeneral= true;
    return this;
  }

  public TaxCalculator withTaxSurcharge() { //附加税
    useSurcharge = true;
    return this;
  }

  public double calculate(Order order) {
    return calculate(order, useRegional, useGeneral, useSurcharge);
  }

  public DoubleUnaryOperator taxFunction = d -> d; //这里是持有一个计算税费的函数

  //方法三的方法
  public TaxCalculator with(DoubleUnaryOperator f) { //这里f是另一个lambda表达式，用来计算不同税费的lambda表达式
    //这里andThen方法将之前lambda和f--lambda结合成新的lambda表达式，并重新给taxFunction赋值，相当于先执行了taxFunction，并将结果又传递给了
    //lambda--f继续执行
    taxFunction = taxFunction.andThen(f);
    return this;
  }

  //方法三的方法
  public double calculateF(Order order) {
    return taxFunction.applyAsDouble(order.getValue());
  }

  public static void main(String[] args) {
    //创建订单
    Order order =
        MixedBuilder.forCustomer("BigBank",
            MixedBuilder.buy(t -> t.quantity(80)
                .stock("IBM")
                .on("NYSE")
                .at(125.00)),
            MixedBuilder.sell(t -> t.quantity(50)
                .stock("GOOGLE")
                .on("NASDAQ")
                .at(125.00)));

    //方法一: 使用传统的方式用税率计算器，计算出订单加上税费后的订单价格
    double value = TaxCalculator.calculate(order, true, false, true);
    System.out.printf("Boolean arguments: %.2f%n", value);

    //方法二: 使用方法链接的方式用税率计算器，计算出订单加上税费后的订单价格
    value = new TaxCalculator().withTaxRegional() //返回的是TaxCalculator，并做好了标记
        .withTaxSurcharge() //返回的也是TaxCalculator，并做好了标记
        .calculate(order); //这里其实和前面的方法一样，也是调用了TaxCalculator.calculate()方法
    System.out.printf("Method chaining: %.2f%n", value);

    //方法三: 使用方法引用和andThen方法结合lambda表达式的方式用税率计算器，计算出订单加上税费后的订单价格
                                //这里Tax::regional方法引用是类::静态方法名
    value = new TaxCalculator().with(Tax::regional) //这里将基于原来的lambda基础上加上了Tax::regional表达式组成了新的lambda
        .with(Tax::surcharge) //又在先前的lambda的基础上加上了Tax::surcharge表达式，又构成了新的lambda(后面可以无限添加)
        .calculateF(order); //执行最终的税费，这里其实是调用了3次DoubleUnaryOperator的applyAsDouble()方法
                      //1.先执行的"DoubleUnaryOperator taxFunction = d -> d"
                      //2.再执行的"DoubleUnaryOperator taxFunction = d -> value * 1.1"
                      //3.最后执行的"DoubleUnaryOperator taxFunction = d -> value * 1.05"并返回执行的最终结果
    System.out.printf("Method references: %.2f%n", value);
  }

}
