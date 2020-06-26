package jp.co.internous.react.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.react.model.domain.MstDestination;
import jp.co.internous.react.model.domain.MstUser;
import jp.co.internous.react.model.form.DestinationForm;
import jp.co.internous.react.model.mapper.MstDestinationMapper;
import jp.co.internous.react.model.mapper.MstUserMapper;
import jp.co.internous.react.model.session.LoginSession;

@Controller
@RequestMapping("/react/destination")
public class DestinationController {

	@Autowired
	LoginSession loginSession;

	@Autowired
	MstUserMapper mstUserMapper;

	@Autowired
	MstDestinationMapper mstDestinationMapper;

	Gson gson = new Gson();

	@RequestMapping("/")
	public String index(Model model) {
		MstUser user = mstUserMapper.findByUserNameAndPassword(loginSession.getUserName(), loginSession.getPassword());
		model.addAttribute("user", user);
		model.addAttribute("loginSession", loginSession);
		return "destination";
	}

	// 宛先削除(DBから論理削除[statusを0に])
	@SuppressWarnings("unchecked")
	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete(@RequestBody String destinationId) {

		Map<String, String> map = gson.fromJson(destinationId, Map.class);
		String id = map.get("destinationId");

		int result = mstDestinationMapper.logicalDeleteById(Integer.parseInt(id));

		return result > 0;
	}

	// 宛先をDBに登録
	@ResponseBody
	@PostMapping("/register")
	public String registerAddress(@RequestBody DestinationForm dstForm) {

		MstDestination mstDestination = new MstDestination();
		int userId = (int) loginSession.getUserId();
		mstDestination.setUserId(userId);

		mstDestination.setFamilyName(dstForm.getFamilyName());
		mstDestination.setFirstName(dstForm.getFirstName());
		mstDestination.setTelNumber(dstForm.getTelNumber());
		mstDestination.setAddress(dstForm.getAddress());
		int count = mstDestinationMapper.insert(mstDestination);

		// 登録した宛先IDを取得
		Integer id = 0;
		if (count > 0) {
			id = mstDestinationMapper.findIdByUserId(userId);
		}
		return id.toString();

	}

}
