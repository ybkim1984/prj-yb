package namoo.board.dom2.da.mongospring.repository.custom;

public interface BoardTeamCustomRepository {

	void deleteByUsidAndMembersUserEmail(String teamUsid, String memberEmail);

}
