package com.thubas.sotiras.controller;

import static java.time.LocalDateTime.now;


import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thubas.sotiras.dto.CheckoutDto;
import com.thubas.sotiras.model.Response;
import com.thubas.sotiras.service.CheckoutService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bookman/checkouts")
@RequiredArgsConstructor
public class CheckoutController {
	
	private final CheckoutService checkoutService;
	
	@GetMapping
	public ResponseEntity<Response> getBookRequests() {
		 return ResponseEntity.ok(
				 Response.builder()
				 .timeStamp(now())
				 .data(Map.of("checkouts", checkoutService.getRequests(30)))
				 .message("Checkouts retrieved")
				 .status(HttpStatus.OK)
				 .statusCode(HttpStatus.OK.value())
				 .build()
			);
	}
	
	
	@PostMapping("/{id}")
	public ResponseEntity<Response> requestBook(@PathVariable Long id) {
		log.info("Creating checkout for book: {}", id);
		CheckoutDto bookRequest = checkoutService.requestBook(id);

		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.statusCode(HttpStatus.CREATED.value())
				.status(HttpStatus.CREATED)
				.message("Book request issued successfully")
				.data(Map.of("bookRequest", bookRequest))
				.build()
		);
		
	}
	
	@GetMapping("/{checkoutId}")
	public ResponseEntity<Response> getBookRequest(@PathVariable Long checkoutId) {
		CheckoutDto bookRequest = checkoutService.getRequest(checkoutId);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("bookRequest", bookRequest))
				.message("Request retrieved")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
			);
	}
	
	@PutMapping("/{id}/approve")
	public ResponseEntity<Response> approveRequest(@PathVariable Long id) {
		CheckoutDto processedRequest = checkoutService.approveRequest(id);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.message("Request approved")
				.data(Map.of("approvedCheckout", processedRequest))
				.build()
			);
	}
	
	
	@DeleteMapping("/{checkoutId}")
	public ResponseEntity<Response> delete(@PathVariable Long checkoutId) {
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("deleted", checkoutService.deleteRequest(checkoutId)))
				.message("Checkout deleted")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
			);
	}

}
