package com.nxt.demo.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nxt.demo.model.Voucher;

@Service
public class VoucherRepositoryImpl implements VoucherRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoucherRepositoryImpl.class);
	private static final String RESOURCE_FILE = "static/voucher.store.json";

	private Map<String, Voucher> voucherMap = new HashMap<>();

	public VoucherRepositoryImpl() throws IOException {
		loadFromFile();
	}

	private void loadFromFile() throws IOException {
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(RESOURCE_FILE);
			List<Voucher> vouchers = Arrays.asList(jsonMapper.readValue(inputStream, Voucher[].class));

			vouchers.stream().forEach(voucher -> {
				Voucher previous = voucherMap.put(voucher.getId(), voucher);
				if (previous != null) {
					LOGGER.warn("Duplicate voucher id: " + voucher.getId());
				}
			});
		} catch (IOException ex) {
			LOGGER.error("Failed to load voucher list from file", ex);
		}
	}

	@Override
	public Optional<Voucher> findVoucherById(String id) {
		Voucher voucher = voucherMap.get(id);
		return Optional.ofNullable(voucher);
	}

}
