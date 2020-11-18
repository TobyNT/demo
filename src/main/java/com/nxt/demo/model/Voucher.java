package com.nxt.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nxt.demo.dto.VoucherRule;

public class Voucher {
	private String id;
	private String type;
	private double value;

	/**
	 * Use to evaluate which types of drinks this voucher applies for.
	 * <br>
	 *  - For including drink types: <code>[vc,ft]</code>
	 * <br>
	 *  - For excluding drink types: <code>![vc,ft]</code>
	 */
	private String expression;
	private List<VoucherRule> rules;

	@JsonProperty("max-discount")
	private Double maxDiscount;

	@JsonProperty("min-invoice-sum")
	private Double minInvoiceSum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Double getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(Double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

	public Double getMinInvoiceSum() {
		return minInvoiceSum;
	}

	public void setMinInvoiceSum(Double minInvoiceSum) {
		this.minInvoiceSum = minInvoiceSum;
	}

	public List<VoucherRule> getRules() {
		return rules;
	}

	public void setRules(List<VoucherRule> rules) {
		this.rules = rules;
	}
}
