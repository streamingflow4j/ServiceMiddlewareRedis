package com.service.middleware.util;

import com.espertech.esper.client.EPStatement;

public class RunTimeEPStatement {
	private EPStatement statement;
	private String expression;

	public RunTimeEPStatement() {
	}

	public RunTimeEPStatement(EPStatement statement, String expression) {
		super();
		this.statement = statement;

		this.expression = expression;
	}

	public EPStatement getStatement() {
		return statement;
	}

	public String getExpression() {
		return expression;
	}

	public void setStatement(EPStatement s) {
		this.statement = s;
	}

	public void destroy() {
		statement.destroy();
	}
}