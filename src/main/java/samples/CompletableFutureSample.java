package samples;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import model.Test;
import model.TestA;
import model.TestB;

public class CompletableFutureSample {

    public static void main(String... args) throws ExecutionException, InterruptedException {

        List<Test> tests = Arrays.asList(new TestA(), new TestB());
        List<CompletableFuture<String>> futures = tests.stream().map(test -> CompletableFuture.supplyAsync(test::message)).collect(Collectors.toList());
        System.out.println(futures.stream().map(CompletableFuture::join).collect(Collectors.joining(" ")));

        CompletableFuture.supplyAsync(() -> new TestA().message())
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> new TestB().message()), CompletableFutureSample::combien)
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> new TestB().message()), CompletableFutureSample::combien)
                .get();
    }

    private static String combien(String s1, String s2) {
        System.out.println(s1.concat(s2));
        return s1.concat(s2);
    }
}
