package jp.co.internous.react.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.react.model.domain.MstUser;
import jp.co.internous.react.model.form.UserForm;
import jp.co.internous.react.model.mapper.MstUserMapper;
import jp.co.internous.react.model.session.LoginSession;

@Controller
@RequestMapping("/react/user")
public class UserController {

	@Autowired
	LoginSession loginSession;

	@Autowired
	MstUserMapper mstUserMapper;

	Gson gson = new Gson();

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("loginSession", loginSession);
		return "register_user";
	}

	// ユーザー名でDBを検索
	@ResponseBody
	@PostMapping("/duplicatedUserName")
	// public booleanで0より大きければTrueを返す
	public boolean getUserName(@RequestParam String userName) {

		int count = mstUserMapper.findCountByUserName(userName);
		return count > 0;

	}

	// 入力されたユーザー情報をDBに登録
	@ResponseBody
	@PostMapping("/register")
	public String registerUser(@RequestBody UserForm userForm) {

		MstUser mstUser = new MstUser();
		mstUser.setUserName(userForm.getUserName());
		mstUser.setPassword(userForm.getPassword());
		mstUser.setFamilyName(userForm.getFamilyName());
		mstUser.setFirstName(userForm.getFirstName());
		mstUser.setFamilyNameKana(userForm.getFamilyNameKana());
		mstUser.setFirstNameKana(userForm.getFirstNameKana());
		mstUser.setGender(userForm.getGender());
		mstUserMapper.insert(mstUser);

		return "register_user";

	}
}
