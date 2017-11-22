package com.lotus.java8Learn.lambda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 把 Lambda 付诸实践：环绕执行模式
 * Created by zhusheng on 2017/11/14 0014.
 */
public class LambdaPractise {
    public static void main(String[] args)throws IOException{
//        String result = processFile((BufferedReader br) -> br.readLine() + br.readLine());

        String oneLine = processFile((BufferedReader br) -> br.readLine());
        System.out.println(oneLine);

        String twoLines = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(twoLines);
    }

    public static String processFile() throws IOException {

        try (BufferedReader br =
                     new BufferedReader(new FileReader("E:\\personnal\\github\\java8Learn\\lambdaLearn\\src\\main\\java\\com\\lotus\\java8Learn\\lambda\\data.txt"))) {
            return br.readLine();
        }
    }

    public static String processFile(BufferedReaderProcessor p) throws
            IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("E:\\personnal\\github\\java8Learn\\lambdaLearn\\src\\main\\java\\com\\lotus\\java8Learn\\lambda\\data.txt"))) {
            return p.process(br);
        }
    }
}
