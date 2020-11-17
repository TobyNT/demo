package com.nxt.demo.model;

import org.junit.jupiter.api.Test;

import com.nxt.demo.dto.BlackListVoucherChecker;
import com.nxt.demo.dto.VoucherChecker;
import com.nxt.demo.dto.VoucherCheckerFactory;
import com.nxt.demo.dto.WhiteListVoucherChecker;

import static org.junit.jupiter.api.Assertions.*;

public class VoucherFactoryTest {
	@Test
	public void testProduceWithEmptyExpression() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> VoucherCheckerFactory.produce(""));
		assertEquals("invalid expression", exception.getMessage());
	}

	@Test
	public void testProduceWithNullExpression() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> VoucherCheckerFactory.produce(null));
		assertEquals("invalid expression", exception.getMessage());
	}

	@Test
	public void testProduceWithInvalidExpression() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> VoucherCheckerFactory.produce("vc,ma"));
		assertEquals("invalid expression", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> VoucherCheckerFactory.produce("[vc,ma"));
		assertEquals("invalid expression", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> VoucherCheckerFactory.produce("vc,ma]"));
		assertEquals("invalid expression", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> VoucherCheckerFactory.produce("[]"));
		assertEquals("invalid expression", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> VoucherCheckerFactory.produce("![]"));
		assertEquals("invalid expression", exception.getMessage());
	}

	@Test
	public void testProduceWhiteListVoucherChecker() {
		VoucherChecker checker = VoucherCheckerFactory.produce("[vc,mc,cb]");
		assertTrue(checker instanceof WhiteListVoucherChecker);
	}

	@Test
	public void testProduceBlackListVoucherChecker() {
		VoucherChecker checker = VoucherCheckerFactory.produce("![vc,mc,cb]");
		assertTrue(checker instanceof BlackListVoucherChecker);
	}
}
