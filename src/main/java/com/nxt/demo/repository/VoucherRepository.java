package com.nxt.demo.repository;

import java.util.Optional;

import com.nxt.demo.model.Voucher;

public interface VoucherRepository {
	Optional<Voucher> findVoucherById(String id);
}
