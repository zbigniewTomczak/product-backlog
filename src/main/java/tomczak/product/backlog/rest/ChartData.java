package tomczak.product.backlog.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChartData {
	public long[][] data;

	public ChartData() {
	}

	public ChartData(long[][] data) {
		this.data = data;
	}
}
