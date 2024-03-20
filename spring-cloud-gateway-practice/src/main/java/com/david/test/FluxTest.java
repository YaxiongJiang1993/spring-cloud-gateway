package com.david.test;

import reactor.core.publisher.Flux;

public class FluxTest {

	public static void main(String[] args) {
		Flux.create((t) -> {
			t.next("create");
			t.next("create1");
			t.complete();
		}).subscribe(System.out::println);
	}
}
