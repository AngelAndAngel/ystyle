
var $c;
if ($FF) {
	Event.prototype.__defineSetter__("returnValue", function ($) {
		if (!$) {
			this.preventDefault();
		}
		return $;
	});
	Event.prototype.__defineGetter__("srcElement", function () {
		var $ = this.target;
		while ($.nodeType != 1) {
			$ = $.parentNode;
		}
		return $;
	});
	HTMLElement.prototype.attachEvent = function ($, _) {
		var A = $.replace(/on/, "");
		_._ieEmuEventHandler = function ($) {
			window.event = $;
			return _();
		};
		this.addEventListener(A, _._ieEmuEventHandler, false);
	};
}
function My97DP() {
	$c = this;
	this.QS = [];
	$d = document.createElement("div");
	$d.className = "WdateDiv";
	$d.innerHTML = "<div id=dpTitle><div class=\"navImg NavImgll\"><a href=\"###\"></a></div><div class=\"navImg NavImgl\"><a href=\"###\"></a></div><div style=\"float:left\"><div class=\"menuSel MMenu\"></div><input class=yminput></div><div style=\"float:left\"><div class=\"menuSel YMenu\"></div><input class=yminput></div><div class=\"navImg NavImgrr\"><a href=\"###\"></a></div><div class=\"navImg NavImgr\"><a href=\"###\"></a></div><div style=\"float:right\"></div></div><div style=\"position:absolute;overflow:hidden\"></div><div></div><div id=dpTime><div class=\"menuSel hhMenu\"></div><div class=\"menuSel mmMenu\"></div><div class=\"menuSel ssMenu\"></div><table cellspacing=0 cellpadding=0 border=0><tr><td rowspan=2><span id=dpTimeStr></span>&nbsp;<input class=tB maxlength=2><input value=\":\" class=tm readonly><input class=tE maxlength=2><input value=\":\" class=tm readonly><input class=tE maxlength=2></td><td><button id=dpTimeUp></button></td></tr><tr><td><button id=dpTimeDown></button></td></tr></table></div><div id=dpQS></div><div id=dpControl><input class=dpButton id=dpClearInput type=button><input class=dpButton id=dpTodayInput type=button><input class=dpButton id=dpOkInput type=button></div>";
	attachTabEvent($d, function () {
		hideSel();
	});
	A();
	$dp.focusArr = [$dp.el, $d.MI, $d.yI, $d.HI, $d.mI, $d.sI, $d.clearI, $d.todayI, $d.okI];
	for (var B = 0; B < $dp.focusArr.length; B++) {
		var _ = $dp.focusArr[B];
		_.nextCtrl = B == $dp.focusArr.length - 1 ? $dp.focusArr[0] : $dp.focusArr[B + 1];
		if (B > 0) {
			$dp.attachEvent(_, "onkeydown", _tab);
		}
	}
	this.init();
	$();
	_inputBindEvent("y,M,H,m,s");
	$d.upButton.onclick = function () {
		updownEvent(1);
	};
	$d.downButton.onclick = function () {
		updownEvent(-1);
	};
	$d.qsDiv.onclick = function () {
		if ($d.qsDivSel.style.display != "block") {
			$c._fillQS();
			showB($d.qsDivSel);
		} else {
			hide($d.qsDivSel);
		}
	};
	document.body.appendChild($d);
	$d.qsDivSel.style.width = $d.dDiv.offsetWidth;
	$d.qsDivSel.style.height = $d.dDiv.offsetHeight;
	function A() {
		var _ = $("a");
		divs = $("div"), ipts = $("input"), btns = $("button"), spans = $("span");
		$d.navLeftImg = _[0];
		$d.leftImg = _[1];
		$d.rightImg = _[3];
		$d.navRightImg = _[2];
		$d.rMD = divs[9];
		$d.MI = ipts[0];
		$d.yI = ipts[1];
		$d.titleDiv = divs[0];
		$d.MD = divs[4];
		$d.yD = divs[6];
		$d.qsDivSel = divs[10];
		$d.dDiv = divs[11];
		$d.tDiv = divs[12];
		$d.HD = divs[13];
		$d.mD = divs[14];
		$d.sD = divs[15];
		$d.qsDiv = divs[16];
		$d.bDiv = divs[17];
		$d.HI = ipts[2];
		$d.mI = ipts[4];
		$d.sI = ipts[6];
		$d.clearI = ipts[7];
		$d.todayI = ipts[8];
		$d.okI = ipts[9];
		$d.upButton = btns[0];
		$d.downButton = btns[1];
		$d.timeSpan = spans[0];
		function $($) {
			return $d.getElementsByTagName($);
		}
	}
	function $() {
		$d.navLeftImg.onclick = function () {
			$ny = $ny <= 0 ? $ny - 1 : -1;
			if ($ny % 5 == 0) {
				$d.yI.focus();
				return;
			}
			$d.yI.value = $dt.y - 1;
			$d.yI.onblur();
		};
		$d.leftImg.onclick = function () {
			$dt.attr("M", -1);
			$d.MI.onblur();
		};
		$d.rightImg.onclick = function () {
			$dt.attr("M", 1);
			$d.MI.onblur();
		};
		$d.navRightImg.onclick = function () {
			$ny = $ny >= 0 ? $ny + 1 : 1;
			if ($ny % 5 == 0) {
				$d.yI.focus();
				return;
			}
			$d.yI.value = $dt.y + 1;
			$d.yI.onblur();
		};
	}
}
My97DP.prototype = {init:function () {
	$ny = 0;
	$dp.cal = this;
	if ($dp.readOnly && $dp.el.readOnly != null) {
		$dp.el.readOnly = true;
		$dp.el.blur();
	}
	$();
	this._dealFmt();
	$dt = this.newdate = new DPDate();
	$tdt = new DPDate();
	$sdt = this.date = new DPDate();
	this.dateFmt = this.doExp($dp.dateFmt);
	this.autoPickDate = $dp.autoPickDate == null ? ($dp.has.st && $dp.has.st ? false : true) : $dp.autoPickDate;
	this.ddateRe = this._initRe("disabledDates");
	this.ddayRe = this._initRe("disabledDays");
	this.sdateRe = this._initRe("specialDates");
	this.sdayRe = this._initRe("specialDays");
	this.minDate = this.doCustomDate($dp.minDate, $dp.minDate != $dp.defMinDate ? $dp.realFmt : $dp.realFullFmt, $dp.defMinDate);
	this.maxDate = this.doCustomDate($dp.maxDate, $dp.maxDate != $dp.defMaxDate ? $dp.realFmt : $dp.realFullFmt, $dp.defMaxDate);
	if (this.minDate.compareWith(this.maxDate) > 0) {
		$dp.errMsg = $lang.err_1;
	}
	if (this.loadDate()) {
		this._makeDateInRange();
		this.oldValue = $dp.el[$dp.elProp];
	} else {
		this.mark(false, 2);
	}
	_setAll($dt);
	$d.timeSpan.innerHTML = $lang.timeStr;
	$d.clearI.value = $lang.clearStr;
	$d.todayI.value = $lang.todayStr;
	$d.okI.value = $lang.okStr;
	$d.okI.disabled = !$c.checkValid($sdt);
	this.initShowAndHide();
	this.initBtn();
	if ($dp.errMsg) {
		alert($dp.errMsg);
	}
	this.draw();
	if ($dp.el.nodeType == 1 && $dp.el["My97Mark"] === undefined) {
		$dp.attachEvent($dp.el, "onkeydown", _tab);
	}
	$c.currFocus = $dp.focusArr[0] = $dp.el;
	$dp.el.nextCtrl = $dp.focusArr[1];
	$dp.focusArr[$dp.focusArr.length - 1].nextCtrl = $dp.focusArr[0];
	hideSel();
	function $() {
		var _, $;
		for (_ = 0; ($ = document.getElementsByTagName("link")[_]); _++) {
			if ($["rel"].indexOf("style") != -1 && $["title"]) {
				$.disabled = true;
				if ($["title"] == $dp.skin) {
					$.disabled = false;
				}
			}
		}
	}
}, _makeDateInRange:function () {
	var _ = this.checkRange();
	if (_ != 0) {
		var $;
		if (_ > 0) {
			$ = this.maxDate;
		} else {
			$ = this.minDate;
		}
		if ($dp.has.sd) {
			$dt.y = $.y;
			$dt.M = $.M;
			$dt.d = $.d;
		}
		if ($dp.has.st) {
			$dt.H = $.H;
			$dt.m = $.m;
			$dt.s = $.s;
		}
	}
}, splitDate:function (J, C, Q, E, B, G, F, K, L) {
	var $;
	if (J && J.loadDate) {
		$ = J;
	} else {
		$ = new DPDate();
		if (J != "") {
			C = C || $dp.dateFmt;
			var H, P = 0, O, A = /yyyy|yyy|yy|y|MMMM|MMM|MM|M|dd|d|%ld|HH|H|mm|m|ss|s|DD|D|WW|W|w/g, _ = C.match(A);
			A.lastIndex = 0;
			if (L) {
				O = J.split(/\W+/);
			} else {
				var D = 0, M = "^";
				while ((O = A.exec(C)) !== null) {
					if (D >= 0) {
						M += C.substring(D, O.index);
					}
					D = O.index - D;
					D = A.lastIndex;
					switch (O[0]) {
					  case "yyyy":
						M += "(\\d{4})";
						break;
					  case "yyy":
						M += "(\\d{3})";
						break;
					  case "MMMM":
					  case "MMM":
					  case "DD":
					  case "D":
						M += "(\\D+)";
						break;
					  default:
						M += "(\\d\\d?)";
						break;
					}
				}
				M += ".*$";
				O = new RegExp(M).exec(J);
				P = 1;
			}
			if (O) {
				for (H = 0; H < _.length; H++) {
					var I = O[H + P];
					if (I) {
						switch (_[H]) {
						  case "MMMM":
						  case "MMM":
							$.M = N(_[H], I);
							break;
						  case "y":
						  case "yy":
							I = pInt2(I, 0);
							if (I < 50) {
								I += 2000;
							} else {
								I += 1900;
							}
							$.y = I;
							break;
						  case "yyy":
							$.y = pInt2(I, 0) + $dp.yearOffset;
							break;
						  default:
							$[_[H].slice(-1)] = I;
							break;
						}
					}
				}
			} else {
				$.d = 32;
			}
		}
	}
	$.coverDate(Q, E, B, G, F, K);
	return $;
	function N(A, $) {
		var _ = A == "MMMM" ? $lang.aLongMonStr : $lang.aMonStr;
		for (var B = 0; B < 12; B++) {
			if (_[B].toLowerCase() == $.substr(0, _[B].length).toLowerCase()) {
				return B + 1;
			}
		}
		return -1;
	}
}, _initRe:function (_) {
	var B, $ = $dp[_], A = "(?:";
	if ($) {
		for (B = 0; B < $.length; B++) {
			A += this.doExp($[B]);
			if (B != $.length - 1) {
				A += "|";
			}
		}
		A = new RegExp(A + ")");
	} else {
		A = null;
	}
	return A;
}, update:function () {
	var $ = this.getNewDateStr();
	if ($dp.el[$dp.elProp] != $) {
		$dp.el[$dp.elProp] = $;
	}
	this.setRealValue();
}, setRealValue:function ($) {
	var _ = $dp.$($dp.vel), $ = rtn($, this.getNewDateStr($dp.realFmt));
	if (_) {
		_.value = $;
	}
	$dp.el["realValue"] = $;
}, doExp:function (s) {
	var ps = "yMdHms", arr, tmpEval, re = /#?\{(.*?)\}/;
	s = s + "";
	for (var i = 0; i < ps.length; i++) {
		s = s.replace("%" + ps.charAt(i), this.getP(ps.charAt(i), null, $tdt));
	}
	if (s.substring(0, 3) == "#F{") {
		s = s.substring(3, s.length - 1);
		if (s.indexOf("return ") < 0) {
			s = "return " + s;
		}
		s = $dp.win.eval("new Function(\"" + s + "\");");
		s = s();
	} else {
		while ((arr = re.exec(s)) != null) {
			arr.lastIndex = arr.index + arr[1].length + arr[0].length - arr[1].length - 1;
			tmpEval = pInt(eval(arr[1]));
			if (tmpEval < 0) {
				tmpEval = "9700" + (-tmpEval);
			}
			s = s.substring(0, arr.index) + tmpEval + s.substring(arr.lastIndex + 1);
		}
	}
	return s;
}, doCustomDate:function (A, B, _) {
	var $;
	A = this.doExp(A);
	if (!A || A == "") {
		A = _;
	}
	if (typeof A == "object") {
		$ = A;
	} else {
		$ = this.splitDate(A, B, null, null, 1, 0, 0, 0, true);
		$.y = ("" + $.y).replace(/^9700/, "-");
		$.M = ("" + $.M).replace(/^9700/, "-");
		$.d = ("" + $.d).replace(/^9700/, "-");
		$.H = ("" + $.H).replace(/^9700/, "-");
		$.m = ("" + $.m).replace(/^9700/, "-");
		$.s = ("" + $.s).replace(/^9700/, "-");
		if (A.indexOf("%ld") >= 0) {
			A = A.replace(/%ld/g, "0");
			$.d = 0;
			$.M = pInt($.M) + 1;
		}
		$.refresh();
	}
	return $;
}, loadDate:function () {
	var _, $;
	if ($dp.alwaysUseStartDate || ($dp.startDate != "" && $dp.el[$dp.elProp] == "")) {
		_ = this.doExp($dp.startDate);
		$ = $dp.realFmt;
	} else {
		_ = $dp.el[$dp.elProp];
		$ = this.dateFmt;
	}
	$dt.loadFromDate(this.splitDate(_, $));
	if (_ != "") {
		var A = 1;
		if ($dp.has.sd && !this.isDate($dt)) {
			$dt.y = $tdt.y;
			$dt.M = $tdt.M;
			$dt.d = $tdt.d;
			A = 0;
		}
		if ($dp.has.st && !this.isTime($dt)) {
			$dt.H = $tdt.H;
			$dt.m = $tdt.m;
			$dt.s = $tdt.s;
			A = 0;
		}
		return A && this.checkValid($dt);
	}
	return 1;
}, isDate:function ($) {
	if ($.y != null) {
		$ = doStr($.y, 4) + "-" + $.M + "-" + $.d;
	}
	return $.match(/^((\d{2}(([02468][048])|([13579][26]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|([1-2][0-9])))))|(\d{2}(([02468][1235679])|([13579][01345789]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\s(((0?[0-9])|([1-2][0-3]))\:([0-5]?[0-9])((\s)|(\:([0-5]?[0-9])))))?$/);
}, isTime:function ($) {
	if ($.H != null) {
		$ = $.H + ":" + $.m + ":" + $.s;
	}
	return $.match(/^([0-9]|([0-1][0-9])|([2][0-3])):([0-9]|([0-5][0-9])):([0-9]|([0-5][0-9]))$/);
}, checkRange:function ($, A) {
	$ = $ || $dt;
	var _ = $.compareWith(this.minDate, A);
	if (_ > 0) {
		_ = $.compareWith(this.maxDate, A);
		if (_ < 0) {
			_ = 0;
		}
	}
	return _;
}, checkValid:function ($, A, B) {
	A = A || $dp.has.minUnit;
	var _ = this.checkRange($, A);
	if (_ == 0) {
		_ = 1;
		if (A == "d") {
			B = B || new Date($.y, $.M - 1, $.d).getDay();
		}
		_ = !this.testDisDay(B) && !this.testDisDate($);
	} else {
		_ = 0;
	}
	return _;
}, checkAndUpdate:function () {
	var _ = $dp.el, A = this, $ = $dp.el[$dp.elProp];
	if ($ != null) {
		if ($ != "" && !$dp.readOnly) {
			A.date.loadFromDate(A.splitDate($, A.dateFmt));
		}
		if ($ == "" || (A.isDate(A.date) && A.isTime(A.date) && A.checkValid(A.date))) {
			if ($ != "") {
				A.newdate.loadFromDate(A.date);
				A.update();
			} else {
				A.setRealValue("");
			}
		} else {
			return false;
		}
	}
	return true;
}, close:function ($) {
	hideSel();
	if (this.checkAndUpdate()) {
		this.mark(true);
		$dp.hide();
	} else {
		if ($) {
			_cancelKey($);
			this.mark(false, 2);
		} else {
			this.mark(false);
		}
	}
}, _fd:function () {
	var E, C, D, K, A, H = new sb(), F = $lang.aWeekStr, G = $dp.firstDayOfWeek, I = "", $ = "", _ = new DPDate($dt.y, $dt.M, $dt.d, 0, 0, 0), J = _.y, B = _.M;
	A = 1 - new Date(J, B - 1, 1).getDay() + G;
	if (A > 1) {
		A -= 7;
	}
	H.a("<table class=WdayTable width=100% border=0 cellspacing=0 cellpadding=0>");
	H.a("<tr class=MTitle align=center>");
	if ($dp.isShowWeek) {
		H.a("<td>" + F[0] + "</td>");
	}
	for (E = 0; E < 7; E++) {
		H.a("<td>" + F[(G + E) % 7 + 1] + "</td>");
	}
	H.a("</tr>");
	for (E = 1, C = A; E < 7; E++) {
		H.a("<tr>");
		for (D = 0; D < 7; D++) {
			_.loadDate(J, B, C++);
			_.refresh();
			if (_.M == B) {
				K = true;
				if (_.compareWith($sdt, "d") == 0) {
					I = "Wselday";
				} else {
					if (_.compareWith($tdt, "d") == 0) {
						I = "Wtoday";
					} else {
						I = ($dp.highLineWeekDay && (0 == (G + D) % 7 || 6 == (G + D) % 7) ? "Wwday" : "Wday");
					}
				}
				$ = ($dp.highLineWeekDay && (0 == (G + D) % 7 || 6 == (G + D) % 7) ? "WwdayOn" : "WdayOn");
			} else {
				if ($dp.isShowOthers) {
					K = true;
					I = "WotherDay";
					$ = "WotherDayOn";
				} else {
					K = false;
				}
			}
			if ($dp.isShowWeek && D == 0 && (E < 4 || K)) {
				H.a("<td class=Wweek>" + getWeek(_, 1) + "</td>");
			}
			H.a("<td ");
			if (K) {
				if (this.checkValid(_, "d", D)) {
					if (this.testSpeDay(new Date(_.y, _.M - 1, _.d).getDay()) || this.testSpeDate(_)) {
						I = "WspecialDay";
					}
					H.a("onclick=\"day_Click(" + _.y + "," + _.M + "," + _.d + ");\" ");
					H.a("onmouseover=\"this.className='" + $ + "'\" ");
					H.a("onmouseout=\"this.className='" + I + "'\" ");
				} else {
					I = "WinvalidDay";
				}
				H.a("class=" + I);
				H.a(">" + _.d + "</td>");
			} else {
				H.a("></td>");
			}
		}
		H.a("</tr>");
	}
	H.a("</table>");
	return H.j();
}, testDisDate:function (_) {
	var $ = this.testDate(_, this.ddateRe);
	return (this.ddateRe && $dp.opposite) ? !$ : $;
}, testDisDay:function ($) {
	return this.testDay($, this.ddayRe);
}, testSpeDate:function ($) {
	return this.testDate($, this.sdateRe, 1);
}, testSpeDay:function ($) {
	return this.testDay($, this.sdayRe, 1);
}, testDate:function ($, _) {
	return _ ? _.test(this.getDateStr($dp.realFmt, $)) : 0;
}, testDay:function (_, $) {
	return $ ? $.test(_) : 0;
}, _f:function (p, c, r, e, isR) {
	var s = new sb(), fp = isR ? "r" + p : p;
	bak = $dt[p];
	s.a("<table cellspacing=0 cellpadding=3 border=0");
	for (var i = 0; i < r; i++) {
		s.a("<tr nowrap=\"nowrap\">");
		for (var j = 0; j < c; j++) {
			s.a("<td nowrap ");
			$dt[p] = eval(e);
			if (($dp.opposite && this.checkRange($dt, p) == 0) || this.checkValid($dt, p)) {
				s.a("class='menu' onmouseover=\"this.className='menuOn'\" onmouseout=\"this.className='menu'\" onmousedown=\"");
				s.a("hide($d." + p + "D);$d." + fp + "I.value=" + $dt[p] + ";$d." + fp + "I.blur();\"");
			} else {
				s.a("class='invalidMenu'");
			}
			s.a(">" + (p == "M" ? $lang.aMonStr[$dt[p] - 1] : $dt[p]) + "</td>");
		}
		s.a("</tr>");
	}
	s.a("</table>");
	$dt[p] = bak;
	return s.j();
}, _fMyPos:function ($, _) {
	if ($) {
		var A = $.offsetLeft;
		if ($IE) {
			A = $.getBoundingClientRect().left;
		}
		_.style.left = A;
	}
}, _fM:function ($) {
	this._fMyPos($, $d.MD);
	$d.MD.innerHTML = this._f("M", 2, 6, "i+j*6+1", $ == $d.rMI);
}, _fy:function (_, A) {
	var $ = new sb();
	A = rtn(A, $dt.y - 5);
	$.a(this._f("y", 2, 5, A + "+i+j*5", _ == $d.ryI));
	$.a("<table cellspacing=0 cellpadding=3 border=0 align=center><tr><td ");
	$.a(this.minDate.y < A ? "class='menu' onmouseover=\"this.className='menuOn'\" onmouseout=\"this.className='menu'\" onmousedown='if(event.preventDefault)event.preventDefault();event.cancelBubble=true;$c._fy(0," + (A - 10) + ")'" : "class='invalidMenu'");
	$.a(">\u2190</td><td class='menu' onmouseover=\"this.className='menuOn'\" onmouseout=\"this.className='menu'\" onmousedown=\"hide($d.yD);$d.yI.blur();\">\xd7</td><td ");
	$.a(this.maxDate.y > A + 10 ? "class='menu' onmouseover=\"this.className='menuOn'\" onmouseout=\"this.className='menu'\" onmousedown='if(event.preventDefault)event.preventDefault();event.cancelBubble=true;$c._fy(0," + (A + 10) + ")'" : "class='invalidMenu'");
	$.a(">\u2192</td></tr></table>");
	this._fMyPos(_, $d.yD);
	$d.yD.innerHTML = $.j();
}, _fHMS:function (A, _, $) {
	$d[A + "D"].innerHTML = this._f(A, 6, _, $);
}, _fH:function () {
	this._fHMS("H", 4, "i * 6 + j");
}, _fm:function () {
	this._fHMS("m", 2, "i * 30 + j * 5");
}, _fs:function () {
	this._fHMS("s", 1, "j * 10");
}, _fillQS:function (A) {
	this.initQS();
	var _ = this.QS, C = _.style, $ = new sb();
	$.a("<table class=WdayTable width=\"100%\" height=\"100%\" border=0 cellspacing=0 cellpadding=0>");
	$.a("<tr class=MTitle><td><div style=\"float:left\">" + $lang.quickStr + "</div>");
	if (!A) {
		$.a("<div style=\"float:right;cursor:pointer\" onclick=\"hide($d.qsDivSel);\">\xd7</div>");
	}
	$.a("</td></tr>");
	for (var B = 0; B < _.length; B++) {
		if (_[B]) {
			$.a("<tr><td style='text-align:left' nowrap='nowrap' class='menu' onmouseover=\"this.className='menuOn'\" onmouseout=\"this.className='menu'\" onclick=\"");
			$.a("day_Click(" + _[B].y + ", " + _[B].M + ", " + _[B].d + "," + _[B].H + "," + _[B].m + "," + _[B].s + ");\">");
			$.a("&nbsp;" + this.getDateStr(null, _[B]));
			$.a("</td></tr>");
		} else {
			$.a("<tr><td class='menu'>&nbsp;</td></tr>");
		}
	}
	$.a("</table>");
	$d.qsDivSel.innerHTML = $.j();
}, _dealFmt:function () {
	$(/w/);
	$(/WW|W/);
	$(/DD|D/);
	$(/yyyy|yyy|yy|y/);
	$(/MMMM|MMM|MM|M/);
	$(/dd|d/);
	$(/HH|H/);
	$(/mm|m/);
	$(/ss|s/);
	$dp.has.sd = ($dp.has.y || $dp.has.M || $dp.has.d) ? true : false;
	$dp.has.st = ($dp.has.H || $dp.has.m || $dp.has.s) ? true : false;
	$dp.realFullFmt = $dp.realFullFmt.replace(/%Date/, $dp.realDateFmt).replace(/%Time/, $dp.realTimeFmt);
	if ($dp.has.sd) {
		if ($dp.has.st) {
			$dp.realFmt = $dp.realFullFmt;
		} else {
			$dp.realFmt = $dp.realDateFmt;
		}
	} else {
		$dp.realFmt = $dp.realTimeFmt;
	}
	function $(_) {
		var $ = (_ + "").slice(1, 2);
		$dp.has[$] = _.exec($dp.dateFmt) ? ($dp.has.minUnit = $, true) : false;
	}
}, initShowAndHide:function () {
	var $ = 0;
	$dp.has.y ? ($ = 1, show($d.yI, $d.navLeftImg, $d.navRightImg)) : hide($d.yI, $d.navLeftImg, $d.navRightImg);
	$dp.has.M ? ($ = 1, show($d.MI, $d.leftImg, $d.rightImg)) : hide($d.MI, $d.leftImg, $d.rightImg);
	$ ? show($d.titleDiv) : hide($d.titleDiv);
	if ($dp.has.st) {
		show($d.tDiv);
		disHMS($d.HI, $dp.has.H);
		disHMS($d.mI, $dp.has.m);
		disHMS($d.sI, $dp.has.s);
	} else {
		hide($d.tDiv);
	}
	shorH($d.clearI, $dp.isShowClear);
	shorH($d.todayI, $dp.isShowToday);
	shorH($d.okI, $dp.isShowOK);
	shorH($d.qsDiv, ($dp.has.d && $dp.qsEnabled));
	if ($dp.eCont || !($dp.isShowClear || $dp.isShowToday || $dp.isShowOK)) {
		hide($d.bDiv);
	}
}, mark:function (B, D) {
	var A = $dp.el, _ = $FF ? "class" : "className";
	if (B) {
		C(A);
	} else {
		if (D == null) {
			D = $dp.errDealMode;
		}
		switch (D) {
		  case 0:
			if (confirm($lang.errAlertMsg)) {
				A[$dp.elProp] = this.oldValue;
				C(A);
			} else {
				$(A);
			}
			break;
		  case 1:
			A[$dp.elProp] = this.oldValue;
			C(A);
			break;
		  case 2:
			$(A);
			break;
		}
	}
	function C(A) {
		var B = A.className;
		if (B) {
			var $ = B.replace(/WdateFmtErr/g, "");
			if (B != $) {
				A.setAttribute(_, $);
			}
		}
	}
	function $($) {
		$.setAttribute(_, $.className + " WdateFmtErr");
	}
}, getP:function (D, _, $) {
	$ = $ || $sdt;
	var H, C = [D + D, D], E, A = $[D], F = function ($) {
		return doStr(A, $.length);
	};
	switch (D) {
	  case "w":
		A = getDay($);
		break;
	  case "D":
		var G = getDay($) + 1;
		F = function ($) {
			return $.length == 2 ? $lang.aLongWeekStr[G] : $lang.aWeekStr[G];
		};
		break;
	  case "W":
		A = getWeek($);
		break;
	  case "y":
		C = ["yyyy", "yyy", "yy", "y"];
		_ = _ || C[0];
		F = function (_) {
			return doStr((_.length < 4) ? (_.length < 3 ? $.y % 100 : ($.y + 2000 - $dp.yearOffset) % 1000) : A, _.length);
		};
		break;
	  case "M":
		C = ["MMMM", "MMM", "MM", "M"];
		F = function ($) {
			return ($.length == 4) ? $lang.aLongMonStr[A - 1] : ($.length == 3) ? $lang.aMonStr[A - 1] : doStr(A, $.length);
		};
		break;
	}
	_ = _ || D + D;
	if ("yMdHms".indexOf(D) > -1 && D != "y" && !$dp.has[D]) {
		if ("Hms".indexOf(D) > -1) {
			A = 0;
		} else {
			A = 1;
		}
	}
	var B = [];
	for (H = 0; H < C.length; H++) {
		E = C[H];
		if (_.indexOf(E) >= 0) {
			B[H] = F(E);
			_ = _.replace(E, "{" + H + "}");
		}
	}
	for (H = 0; H < B.length; H++) {
		_ = _.replace(new RegExp("\\{" + H + "\\}", "g"), B[H]);
	}
	return _;
}, getDateStr:function (_, $) {
	$ = $ || $sdt;
	_ = _ || this.dateFmt;
	if (_.indexOf("%ld") >= 0) {
		var A = new DPDate();
		A.loadFromDate($);
		A.d = 0;
		A.M = pInt(A.M) + 1;
		A.refresh();
		_ = _.replace(/%ld/g, A.d);
	}
	var B = "ydHmswW";
	for (var D = 0; D < B.length; D++) {
		var C = B.charAt(D);
		_ = this.getP(C, _, $);
	}
	if ($dp.has["D"]) {
		_ = _.replace(/DD/g, "%dd").replace(/D/g, "%d");
		_ = this.getP("M", _, $);
		_ = _.replace(/\%dd/g, this.getP("D", "DD")).replace(/\%d/g, this.getP("D", "D"));
	} else {
		_ = this.getP("M", _, $);
	}
	return _;
}, getNewP:function (_, $) {
	return this.getP(_, $, $dt);
}, getNewDateStr:function ($) {
	return this.getDateStr($, $dt);
}, draw:function () {
	$d.rMD.innerHTML = "";
	if ($dp.doubleCalendar) {
		$c.autoPickDate = true;
		$dp.isShowOthers = false;
		$d.className = "WdateDiv WdateDiv2";
		var $ = new sb();
		$.a("<table class=WdayTable2 width=100% cellspacing=0 cellpadding=0 border=1><tr><td valign=top>");
		$.a(this._fd());
		$.a("</td><td valign=top>");
		$dt.attr("M", 1);
		$.a(this._fd());
		$d.rMI = $d.MI.cloneNode(true);
		$d.ryI = $d.yI.cloneNode(true);
		$d.rMD.appendChild($d.rMI);
		$d.rMD.appendChild($d.ryI);
		$d.rMI.value = $lang.aMonStr[$dt.M - 1];
		$d.rMI["realValue"] = $dt.M;
		$d.ryI.value = $dt.y;
		_inputBindEvent("rM,ry");
		$d.rMI.className = $d.ryI.className = "yminput";
		$dt.attr("M", -1);
		$.a("</td></tr></table>");
		$d.dDiv.innerHTML = $.j();
	} else {
		$d.className = "WdateDiv";
		$d.dDiv.innerHTML = this._fd();
	}
	if (!$dp.has.d) {
		this._fillQS(true);
		showB($d.qsDivSel);
	} else {
		hide($d.qsDivSel);
	}
	this.autoSize();
}, autoSize:function () {
	var _ = parent.document.getElementsByTagName("iframe");
	for (var C = 0; C < _.length; C++) {
		var $ = $d.style.height;
		$d.style.height = "";
		var A = $d.offsetHeight;
		if (_[C].contentWindow == window && A) {
			_[C].style.width = $d.offsetWidth + "px";
			var B = $d.tDiv.offsetHeight;
			if (B && $d.bDiv.style.display == "none" && $d.tDiv.style.display != "none" && document.body.scrollHeight - A >= B) {
				A += B;
				$d.style.height = A;
			} else {
				$d.style.height = $;
			}
			_[C].style.height = Math.max(A, $d.offsetHeight) + "px";
		}
	}
}, pickDate:function () {
	while (!this.isDate($dt) && $dt.d > 0) {
		$dt.d--;
	}
	this.update();
	if (!$dp.eCont) {
		if (this.checkValid($dt)) {
			$c.mark(true);
			$dp.el["My97Mark"] = true;
			$dp.el.focus();
			hide($dp.dd);
		} else {
			$c.mark(false);
		}
	}
	if ($dp.onpicked) {
		callFunc("onpicked");
	} else {
		if (this.oldValue != $dp.el[$dp.elProp] && $dp.el.onchange) {
			fireEvent($dp.el, "change");
		}
	}
}, initBtn:function () {
	$d.clearI.onclick = function () {
		if (!callFunc("onclearing")) {
			$dp.el[$dp.elProp] = "";
			$c.setRealValue("");
			$dp.el["My97Mark"] = true;
			$dp.el.focus();
			hide($dp.dd);
			if ($dp.oncleared) {
				callFunc("oncleared");
			} else {
				if ($c.oldValue != $dp.el[$dp.elProp] && $dp.el.onchange) {
					fireEvent($dp.el, "change");
				}
			}
		}
	};
	$d.okI.onclick = function () {
		day_Click();
	};
	if (this.checkValid($tdt)) {
		$d.todayI.disabled = false;
		$d.todayI.onclick = function () {
			$dt.loadFromDate($tdt);
			day_Click();
		};
	} else {
		$d.todayI.disabled = true;
	}
}, initQS:function () {
	var H, G, A, F, C = [], $ = 5, E = $dp.quickSel.length, _ = $dp.has.minUnit;
	if (E > $) {
		E = $;
	} else {
		if (_ == "m" || _ == "s") {
			C = [0, 15, 30, 45, 59, -60, -45, -30, -15, -1];
		} else {
			for (H = 0; H < $ * 2; H++) {
				C[H] = $dt[_] - $ + 1 + H;
			}
		}
	}
	for (H = G = 0; H < E; H++) {
		A = this.doCustomDate($dp.quickSel[H]);
		if (this.checkValid(A)) {
			this.QS[G++] = A;
		}
	}
	var B = "yMdHms", D = [1, 1, 1, 0, 0, 0];
	for (H = 0; H <= B.indexOf(_); H++) {
		D[H] = $dt[B.charAt(H)];
	}
	for (H = 0; G < $; H++) {
		if (H < C.length) {
			A = new DPDate(D[0], D[1], D[2], D[3], D[4], D[5]);
			A[_] = C[H];
			A.refresh();
			if (this.checkValid(A)) {
				this.QS[G++] = A;
			}
		} else {
			this.QS[G++] = null;
		}
	}
}};
function sb() {
	this.s = new Array();
	this.i = 0;
	this.a = function ($) {
		this.s[this.i++] = $;
	};
	this.j = function () {
		return this.s.join("");
	};
}
function getWeek($, C) {
	C = C || 0;
	var _ = new Date($.y, $.M - 1, $.d + C), B = _.getDay();
	_.setDate(_.getDate() - (B + 6) % 7 + 3);
	var A = _.valueOf();
	_.setMonth(0);
	_.setDate(4);
	return Math.round((A - _.valueOf()) / (7 * 86400000)) + 1;
}
function getDay($) {
	var _ = new Date($.y, $.M - 1, $.d);
	return _.getDay();
}
function show() {
	setDisp(arguments, "");
}
function showB() {
	setDisp(arguments, "block");
}
function hide() {
	setDisp(arguments, "none");
}
function setDisp(_, $) {
	for (i = 0; i < _.length; i++) {
		_[i].style.display = $;
	}
}
function shorH(_, $) {
	$ ? show(_) : hide(_);
}
function disHMS(_, $) {
	if ($) {
		_.disabled = false;
	} else {
		_.disabled = true;
		_.value = "00";
	}
}
function c(p, pv, notDraw) {
	if (p == "M") {
		pv = makeInRange(pv, 1, 12);
	} else {
		if (p == "H") {
			pv = makeInRange(pv, 0, 23);
		} else {
			if ("ms".indexOf(p) >= 0) {
				pv = makeInRange(pv, 0, 59);
			}
		}
	}
	if ($sdt[p] != pv && !callFunc(p + "changing")) {
		var func = "sv(\"" + p + "\"," + pv + ")", rv = $c.checkRange();
		if (rv == 0) {
			eval(func);
		} else {
			if (rv < 0) {
				_setFrom($c.minDate);
			} else {
				if (rv > 0) {
					_setFrom($c.maxDate);
				}
			}
		}
		$d.okI.disabled = !$c.checkValid($sdt);
		if (!notDraw && "yMd".indexOf(p) >= 0) {
			$c.draw();
		}
		callFunc(p + "changed");
	}
	function _setFrom($) {
		_setAll($c.checkValid($) ? $ : $sdt);
	}
}
function _setAll($) {
	sv("y", $.y);
	sv("M", $.M);
	sv("d", $.d);
	sv("H", $.H);
	sv("m", $.m);
	sv("s", $.s);
}
function day_Click(F, B, _, D, C, A) {
	var $ = new DPDate($dt.y, $dt.M, $dt.d, $dt.H, $dt.m, $dt.s);
	$dt.loadDate(F, B, _, D, C, A);
	if (!callFunc("onpicking")) {
		var E = $.y == F && $.M == B && $.d == _;
		if (!E && arguments.length != 0) {
			c("y", F, true);
			c("M", B, true);
			c("d", _);
			$c.currFocus = $dp.focusArr[0];
			if ($dp.autoUpdateOnChanged) {
				$c.update();
			}
		}
		if ($c.autoPickDate || E || arguments.length == 0) {
			$c.pickDate();
		}
	} else {
		$dt = $;
	}
}
function callFunc($) {
	var _;
	if ($dp[$]) {
		_ = $dp[$].call($dp.el, $dp);
	}
	return _;
}
function sv(_, $) {
	$ = $ || $dt[_];
	$sdt[_] = $dt[_] = $;
	if ("yHms".indexOf(_) >= 0) {
		$d[_ + "I"].value = $;
	}
	if (_ == "M") {
		$d.MI["realValue"] = $;
		$d.MI.value = $lang.aMonStr[$ - 1];
	}
}
function makeInRange(_, $, A) {
	if (_ < $) {
		_ = $;
	} else {
		if (_ > A) {
			_ = A;
		}
	}
	return _;
}
function attachTabEvent($, _) {
	$.attachEvent("onkeydown", function () {
		var $ = event, A = ($.which == undefined) ? $.keyCode : $.which;
		if (A == 9) {
			_();
		}
	});
}
function doStr($, _) {
	$ = $ + "";
	while ($.length < _) {
		$ = "0" + $;
	}
	return $;
}
function hideSel() {
	hide($d.yD, $d.MD, $d.HD, $d.mD, $d.sD);
}
function updownEvent($) {
	if ($c.currFocus == undefined) {
		$c.currFocus = $d.HI;
	}
	switch ($c.currFocus) {
	  case $d.HI:
		c("H", $dt.H + $);
		break;
	  case $d.mI:
		c("m", $dt.m + $);
		break;
	  case $d.sI:
		c("s", $dt.s + $);
		break;
	}
}
function DPDate(D, A, $, C, B, _) {
	this.loadDate(D, A, $, C, B, _);
}
DPDate.prototype = {loadDate:function (E, B, _, D, C, A) {
	var $ = new Date();
	this.y = pInt3(E, this.y, $.getFullYear());
	this.M = pInt3(B, this.M, $.getMonth() + 1);
	this.d = $dp.has.d ? pInt3(_, this.d, $.getDate()) : 1;
	this.H = pInt3(D, this.H, $.getHours());
	this.m = pInt3(C, this.m, $.getMinutes());
	this.s = pInt3(A, this.s, $.getSeconds());
}, loadFromDate:function ($) {
	if ($) {
		this.loadDate($.y, $.M, $.d, $.H, $.m, $.s);
	}
}, coverDate:function (E, B, _, D, C, A) {
	var $ = new Date();
	this.y = pInt3(this.y, E, $.getFullYear());
	this.M = pInt3(this.M, B, $.getMonth() + 1);
	this.d = $dp.has.d ? pInt3(this.d, _, $.getDate()) : 1;
	this.H = pInt3(this.H, D, $.getHours());
	this.m = pInt3(this.m, C, $.getMinutes());
	this.s = pInt3(this.s, A, $.getSeconds());
}, compareWith:function ($, C) {
	var A = "yMdHms", _, B;
	C = A.indexOf(C);
	C = C >= 0 ? C : 5;
	for (var D = 0; D <= C; D++) {
		B = A.charAt(D);
		_ = this[B] - $[B];
		if (_ > 0) {
			return 1;
		} else {
			if (_ < 0) {
				return -1;
			}
		}
	}
	return 0;
}, refresh:function () {
	var $ = new Date(this.y, this.M - 1, this.d, this.H, this.m, this.s);
	this.y = $.getFullYear();
	this.M = $.getMonth() + 1;
	this.d = $.getDate();
	this.H = $.getHours();
	this.m = $.getMinutes();
	this.s = $.getSeconds();
	return !isNaN(this.y);
}, attr:function (_, $) {
	if ("yMdHms".indexOf(_) >= 0) {
		var A = this.d;
		if (_ == "M") {
			this.d = 1;
		}
		this[_] += $;
		this.refresh();
		this.d = A;
	}
}};
function pInt($) {
	return parseInt($, 10);
}
function pInt2($, _) {
	return rtn(pInt($), _);
}
function pInt3($, A, _) {
	return pInt2($, rtn(A, _));
}
function rtn($, _) {
	return $ == null || isNaN($) ? _ : $;
}
function fireEvent(A, $) {
	if ($IE) {
		A.fireEvent("on" + $);
	} else {
		var _ = document.createEvent("HTMLEvents");
		_.initEvent($, true, true);
		A.dispatchEvent(_);
	}
}
function _foundInput($) {
	var A, B, _ = "y,M,H,m,s,ry,rM".split(",");
	for (B = 0; B < _.length; B++) {
		A = _[B];
		if ($d[A + "I"] == $) {
			return A.slice(A.length - 1, A.length);
		}
	}
	return 0;
}
function _focus($) {
	var _ = _foundInput(this);
	if (!_) {
		return;
	}
	$c.currFocus = this;
	if (_ == "y") {
		this.className = "yminputfocus";
	} else {
		if (_ == "M") {
			this.className = "yminputfocus";
			this.value = this["realValue"];
		}
	}
	this.select();
	$c["_f" + _](this);
	showB($d[_ + "D"]);
}
function _blur(showDiv) {
	var p = _foundInput(this), isR, v = this.value, oldv = $dt[p];
	$dt[p] = pInt2(v, $dt[p]);
	if (p == "y") {
		isR = this == $d.ryI;
		if (isR && $dt.M == 12) {
			$dt.y -= 1;
		}
	} else {
		if (p == "M") {
			isR = this == $d.rMI;
			if (isR) {
				if (oldv == 12) {
					$dt.y += 1;
				}
				$dt.attr("M", -1);
			}
			if ($sdt.M == $dt.M) {
				this.value = $lang.aMonStr[$dt[p] - 1];
			}
		}
	}
	eval("c(\"" + p + "\"," + $dt[p] + ")");
	if (showDiv !== true) {
		if (p == "y" || p == "M") {
			this.className = "yminput";
		}
		hide($d[p + "D"]);
	}
	if ($dp.autoUpdateOnChanged) {
		$c.update();
	}
}
function _cancelKey($) {
	if ($.preventDefault) {
		$.preventDefault();
		$.stopPropagation();
	} else {
		$.cancelBubble = true;
		$.returnValue = false;
	}
	if ($OPERA) {
		$.keyCode = 0;
	}
}
function _inputBindEvent($) {
	var A = $.split(",");
	for (var B = 0; B < A.length; B++) {
		var _ = A[B] + "I";
		$d[_].onfocus = _focus;
		$d[_].onblur = _blur;
	}
}
function _tab($) {
	var B = $.srcElement || $.target, C = $.which || $.keyCode;
	if (C == 27) {
		if (B != $dp.focusArr[0]) {
			B.blur();
		}
		$c.close();
		return;
	}
	if (C == 9) {
		var A = B.nextCtrl || $dp.focusArr[1];
		if (B == $dp.focusArr[0]) {
			$c.close($);
		}
		for (var D = 0; D < $dp.focusArr.length; D++) {
			if (A.disabled == true || A.offsetHeight == 0) {
				A = A.nextCtrl;
			}
		}
		if ($c.currFocus != A) {
			$c.currFocus = A;
			A.focus();
		}
	} else {
		if (C >= 37 && C <= 40) {
			var _;
			if ($c.currFocus == $dp.focusArr[0]) {
				if ($dp.has.d) {
					_ = "d";
					if (C == 38) {
						$dt[_] -= 7;
					} else {
						if (C == 39) {
							$dt[_] += 1;
						} else {
							if (C == 37) {
								$dt[_] -= 1;
							} else {
								$dt[_] += 7;
							}
						}
					}
					$dt.refresh();
					c("y", $dt["y"], true);
					c("M", $dt["M"], true);
					c("d", $dt[_]);
					return;
				} else {
					_ = $dp.has.minUnit;
					$d[_ + "I"].focus();
				}
			}
			_ = _ || _foundInput($c.currFocus);
			if (_) {
				if (C == 38 || C == 39) {
					$dt[_] += 1;
				} else {
					$dt[_] -= 1;
				}
				$dt.refresh();
				$c.currFocus.value = $dt[_];
				_blur.call($c.currFocus, true);
				$c.currFocus.select();
			}
		} else {
			if (C == 13) {
				$c.currFocus.blur();
				if ($c.currFocus.type == "button") {
					$c.currFocus.click();
				} else {
					$c.pickDate();
				}
			}
		}
	}
	if ($c.currFocus != $dp.focusArr[0] && !((C >= 48 && C <= 57) || (C >= 96 && C <= 105) || C == 8 || C == 46)) {
		_cancelKey($);
	}
}

