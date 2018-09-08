package com.lotus.java8Learn.optional;

import java.util.Optional;
import java.util.Properties;

/**
 * Created by zhusheng on 2018/1/2 0002.
 */
public class OptinalSample {
    public static void main(String[] args){
        Integer result1 = readDuration(new Properties(),"11");
        System.out.println("result1:"+result1);
        Integer result2 = readDuration(new Properties(),"11");
        System.out.println("result2:"+result2);
    }

    /**
     * 创建 Optional 对象
     */
    public static void createOptinal(){
        //1. 声明一个空的 Optional
        Optional<Car> optCar = Optional.empty();

        //2. 依据一个非空值创建 Optional
        Car car = new Car();
        Optional<Car> optCar2 = Optional.of(car);

        //3.可接受 null 的 Optional
        Optional<Car> optCar3 = Optional.ofNullable(car);
    }

    /**
     * 使用 map 从 Optional 对象中提取和转换值
     */
    public static void mapOptional(){
        Insurance insurance = new Insurance();
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
        Optional<String> name = optInsurance.map(Insurance::getName);
    }

    /**
     * 使用 flatMap 链接 Optional 对象
     *
     */
    public static void flatMapOptional(){
        //无法编译
        /*Optional<Person> optPerson = Optional.of(person);
        Optional<String> name =
                optPerson.map(Person::getCar)
                        .map(Car::getInsurance)
                        .map(Insurance::getName);*/
    }

    /**
     * 使用 flatMap 链接 Optional 对象
     */
    public String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

    /**
     * 我们决定采用 orElse 方法读取这个变量的值，使用这种方式你还可以定义一个默认值，遭
     * 遇空的 Optional 变量时，默认值会作为该方法的调用返回值。 Optional 类提供了多种方法读取
     * Optional 实例中的变量值。
     *   get() 是这些方法中最简单但又最不安全的方法。如果变量存在，它直接返回封装的变量
     * 值，否则就抛出一个 NoSuchElementException 异常。所以，除非你非常确定 Optional
     * 变量一定包含值，否则使用这个方法是个相当糟糕的主意。此外，这种方式即便相对于
     * 嵌套式的 null 检查，也并未体现出多大的改进。
     *   orElse(T other) 是我们在代码清单10-5中使用的方法，正如之前提到的，它允许你在
     * Optional 对象不包含值时提供一个默认值。
     *   orElseGet(Supplier<? extends T> other) 是 orElse 方法的延迟调用版， Supplier
     * 方法只有在 Optional 对象不含值时才执行调用。如果创建默认值是件耗时费力的工作，
     * 你应该考虑采用这种方式（借此提升程序的性能），或者你需要非常确定某个方法仅在
     * Optional 为空时才进行调用，也可以考虑该方式（这种情况有严格的限制条件）。
     *   orElseThrow(Supplier<? extends X> exceptionSupplier) 和 get 方法非常类似，
     * 它们遭遇 Optional 对象为空时都会抛出一个异常，但是使用 orElseThrow 你可以定制希
     * 望抛出的异常类型。
     *   ifPresent(Consumer<? super T>) 让你能在变量值存在时执行一个作为参数传入的
     * 方法，否则就不进行任何操作。
     * Optional 类和 Stream 接口的相似之处，远不止 map 和 flatMap 这两个方法。还有第三个方
     * 法 filter ，它的行为在两种类型之间也极其相似，我们会在10.3.6节做进一步的介绍。
     */

    /**
     * 两个 Optional 对象的组合
     * @param person
     * @param car
     * @return
     */
    public Optional<Insurance> nullSafeFindCheapestInsurance(
            Optional<Person> person, Optional<Car> car) {

        /*if (person.isPresent() && car.isPresent()) {
            return Optional.of(findCheapestInsurance(person.get(), car.get()));
        } else {
            return Optional.empty();
        }*/

        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    private Insurance findCheapestInsurance(Person person, Car car) {
        // 不同的保险公司提供的查询服务
        // 对比所有数据
        return new Insurance();
    }


    /**
     * 命令式获取properties对象的属性
     * @param props
     * @param name
     * @return
     */
    public static int readDuration(Properties props, String name) {
        String value = props.getProperty(name);
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0) {
                    return i;
                }
            } catch (NumberFormatException nfe) { }
        }
        return 0;
    }


    /**
     * 使用Optional获取properties对象的属性
     * @param props
     * @param name
     * @return
     */
    public static int readDuration2(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(OptionalUtility::stringToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }

}
