package jp.co.internous.react.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.internous.react.model.domain.MstUser;


@Mapper
public interface MstUserMapper {
	
	//ログイン情報をDBから検索
	 @Select("SELECT * FROM mst_user WHERE user_name = #{userName} AND password = #{password}")
	 MstUser findByUserNameAndPassword(@Param("userName")String userName, @Param("password")String password);
	 
	//ユーザー名でDBを検索
	@Select("SELECT count(id) FROM mst_user WHERE user_name = #{userName}")
	//findCount→ユーザー名に一致する行数をカウント
	int findCountByUserName(@Param("userName") String userName);
	
	//ユーザー情報をDBにインサート
	@Insert("INSERT INTO mst_user(user_name,password,family_name,first_name,family_name_kana,first_name_kana,gender)" + 
			"VALUES(#{userName},#{password},#{familyName},#{firstName},#{familyNameKana},#{firstNameKana},#{gender})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	void insert(MstUser mstUser);
	
	//パスワード再設定時、mst_userのパスワードを更新
	@Update("UPDATE mst_user SET password = #{password} WHERE user_name = #{userName}")
	 int updatePassword(
	            @Param("userName") String userName,
	            @Param("password") String password);
	
}