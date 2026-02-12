package com.david.test;

import com.google.common.collect.Lists;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxTest {

	public static void main(String[] args) {

		// testFlux2Mono();
//		testFlux();
//		testConcatMap();
//		testFilter();
		testFilterWhen();
	}

	private static void test1() {
		System.out.println("====================1===============================");
		Flux.create((t) -> {
			t.next("create");
			t.next("create1");
			t.complete();
		}).subscribe(System.out::println);

		System.out.println("====================2===============================");
		// 单个元素
		Flux.just("just").subscribe(System.out::println);
		// 多个元素
		Flux.just("just", "just1", "just2").subscribe(System.out::println);

	}

	private static void testEmpty() {
		Flux.empty().map(e -> {
			System.out.println("hello");
			return 1;
		}).subscribe();
	}

	private static void testConcatMap() {
		List<Integer> data = Lists.newArrayList(1, 2, 3);
		Flux.fromIterable(data)
				// .concatMap(FluxTest::getConcatFlux)
				/*
				 * .concatMap(e->Mono.just(e) .filterWhen(e->e%2==1) )
				 */
				// .log()
				.subscribe(System.out::println);
	}

	private static Flux<Integer> getConcatFlux(Integer i) {
		if (i == 2) {
			return Flux.empty();
		}
		return Flux.just(i, i);
	}

	private static void testFlux() {
		// 创建一个Flux对象，包含三个整数元素
		Flux<Integer> flux = Flux.just(6, 1, 2, 3, 9).concatMap(e -> Flux.just(e + 1));

		// 订阅这个Mono对象，并打印元素值
		flux.subscribe(System.out::println);

	}

	private static void testFlux2Mono() {
		// 创建一个Flux对象，包含三个整数元素
		Flux<Integer> flux = Flux.just(6, 1, 2, 3);

		// 使用next()方法把Flux转换成Mono
		Mono<Integer> mono = flux.next();

		// 订阅这个Mono对象，并打印元素值
		mono.subscribe(System.out::println);

	}

	private static void testFilter() {
		Integer[] fArray = new Integer[]{1, 2, 3, 4};
		Flux.fromArray(fArray)
				.filter(item -> {
					return item % 2 == 0;
				})
				.next()
				.subscribe(result -> {
					System.out.println(result);
				});
	}

	private static void testFilterWhen() {
		//测试一，结果2 4
		Integer[] fwArray = new Integer[]{1, 2, 3, 4};
		Flux.fromArray(fwArray).filterWhen(item -> {
			return Mono.just(item % 2 == 0);
		}).subscribe(result -> {
			System.out.println(result);
		});

		System.out.println("====================================");

		//测试二，结果 1 2 3 4，因为考虑Flux.just(true,false,false)
		//的第一个值true
		Flux.fromArray(fwArray).filterWhen(item -> {
			return Flux.just(true, false, false);
		}).subscribe(result -> {
			System.out.println(result);
		});
		System.out.println("====================================");

//测试三，结果空，因为考虑Flux.just(false, true, false)
//的第一个值false
		Flux.fromArray(fwArray).filterWhen(item -> {
			return Flux.just(false, true, false);
		}).subscribe(result -> {
			System.out.println(result);
		});

	}

}
