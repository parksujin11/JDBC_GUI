package test.dto;

public class MemberDto {
	private int num;
	private String name;
	private String nickname;
	
	public MemberDto() {
		
	}

	public MemberDto(int num, String name, String nickname) {
		super();
		this.num = num;
		this.name = name;
		this.nickname = nickname;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
