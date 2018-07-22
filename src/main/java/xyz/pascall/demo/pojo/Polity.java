package xyz.pascall.demo.pojo;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Polity implements Serializable{

	private static final long serialVersionUID = 2724888087391664167L;

	@NotNull(message = "id不能为null")
	private Integer id;
	@NotBlank(message = "名字不能为null或空字符")
	@Size(min = 1, max = 10, message = "名字长度必须在1~10之间")
	private String name;
	@NotNull(message = "状态不能为null")
	@Range(min = 0, max = 1, message = "状态的值必须在0~1之间")
	private Integer status;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Polity [id=" + id + ", name=" + name + ", status=" + status + "]";
	}
}
