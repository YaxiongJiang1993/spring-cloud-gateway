package com.david.test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxTest {

	public static void main(String[] args) {

//		testFlux2Mono();
		testFlux();
	}

	private static void test1() {
		System.out.println("====================1===============================");
		Flux.create((t) -> {
			t.next("create");
			t.next("create1");
			t.complete();
		}).subscribe(System.out::println);

		System.out.println("====================2===============================");
		//单个元素
		Flux.just("just")
				.subscribe(System.out::println);
		//多个元素
		Flux.just("just", "just1", "just2")
				.subscribe(System.out::println);

	}

	private static void testFlux() {
		// 创建一个Flux对象，包含三个整数元素
		Flux<Integer> flux=Flux.just(6, 1, 2, 3)
				.concatMap(e -> Flux.just(e + 1));


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
}
