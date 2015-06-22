package samples;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import model.Test;
import model.TestA;
import model.TestB;
import model.TestC;

public class CompletableFutureSample {

    public static void main(String... args) throws ExecutionException, InterruptedException {

        //examples for stream
        System.out.println("=======================examples for stream=======================");
        List<Test> tests = Arrays.asList(new TestA(), new TestB());
        List<CompletableFuture<String>> futures = tests.stream().map(test -> CompletableFuture.supplyAsync(test::message)).collect(Collectors.toList());
        System.out.println(futures.stream().map(CompletableFuture::join).collect(Collectors.joining(" ")));

        //example for combine async
        System.out.println("=======================example for combine async=======================");
        CompletableFuture.supplyAsync(() -> new TestA().message())
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> new TestB().message()), CompletableFutureSample::combien)
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> new TestB().message()), CompletableFutureSample::combien)
                .get();

        //example for thenApplyAsync
        System.out.println("=======================example for thenApplyAsync=======================");
        System.out.println(CompletableFuture.supplyAsync(() -> new TestA().message())
                .thenApplyAsync(s -> new TestC().message(s))
                .get());

        //example for thenCompose
        System.out.println("=======================example for thenCompose=======================");
        CompletableFuture<String> futureFlow = CompletableFuture.supplyAsync(() -> new TestA().message())
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> new TestC().message(s)));
        System.out.println(futureFlow.get());

        //example TestC should be exectued when TestA and TestB have finished
        CompletableFuture<String> testACompletableFuture = CompletableFuture.supplyAsync(() -> new TestA().message());
        CompletableFuture<String> testBCompletableFuture = CompletableFuture.supplyAsync(() -> new TestB().message());
        testACompletableFuture
                .thenCombineAsync(testBCompletableFuture, CompletableFutureSample::combien)
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> new TestC().message(s)))
                .get();
    }

    private static String combien(String s1, String s2) {
        System.out.println(s1.concat(s2));
        return s1.concat(s2);
    }
}
