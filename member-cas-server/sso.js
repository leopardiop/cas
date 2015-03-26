define(function (require, exports, module) {

    var $ = require("jquery");

    var des = require("js/lib/des");


    exports.SsoController = function (serviceRelativeURL, domain, server) {
        this.server = server + "/login";
        this.service = domain + serviceRelativeURL;
        this.serviceRelativeURL = serviceRelativeURL;
        this.serverImage = server + "/images/captcha.htm";

        this.captchaEnabled = false;
        this.captchaEnabledId;
        this.dataPool;
        this.count = 0;
        var obj = this;
        this.flushLogin = function (setting) {
            var _services = 'service=' + encodeURIComponent(obj.service);
            var receiver = (setting && setting.receiver) || "";
            var url = obj.server + '?' + _services + '&get-lt=true&receiver=' + receiver;
            obj.jsonp(url, null, setting);
        };

        this.enableCaptcha = function (setting) {
            obj.captchaEnabled = true;
            obj.captchaEnabledId = setting.id;
            obj.changeCaptcha(obj.captchaEnabledId);
            $("#" + obj.captchaEnabledId).click(function () {
                obj.changeCaptcha(obj.captchaEnabledId);
            });
        };
        this.changeCaptcha = function (id) {
            var src = obj.serverImage + "?" + Math.floor(Math.random() * 1000);
            $("#" + id).attr("src", src);
        };

        this.jsonp = function (url, data, setting) {
            $.ajax({
                url: url,
                data: data || {},
                type: "post",
                dataType: "jsonp",
                cache: false,
                jsonp: "callback",
                jsonpCallback: "callback",
                success: function (data) {
                    obj.parseCallback(data, setting);
                }
            });
        };

        this.submit = function (setting) {

            var username = setting.username;
            var password = setting.password;
            var captcha = setting.captcha;
            var receiver = setting.receiver || "";

            if (!setting.sendBefore()) {
                return false;
            }

            if (!obj.dataPool) {
                alert("服务器繁忙，请刷新再试。");
            }

            password = des.strEnc(password, username, username, null);


            var _services = 'service=' + encodeURIComponent(obj.service);
            var url = obj.server + '?' + _services + '&n=' + new Date().getTime();

            var data = {username: username, password: password, captcha: captcha,
                lt: obj.dataPool.lt, _eventId: "submit", execution: obj.dataPool.execution,receiver:receiver };


            this.jsonp(url, data, setting);
        };

        this.parseCallback = function (data, setting) {
            obj.refresh();
            //console && console.log && console.log(data)
            if (data.login == "success") {
                var ticket = data.ticket;
                //console && console.log && console.log(ticket);
                if ($.trim(ticket)) {
                    var casForm = $("<form action='" + this.serviceRelativeURL + "' method='post' ><input type='hidden' name='ticket' value='" + ticket + "' /></form>");
                    $("body").prepend(casForm);
                    //console && console.log && console.log(casForm);
                    casForm.submit();
                }
                else {
                    if (setting && typeof(setting.unauthorized) == "function") {
                        setting.unauthorized();
                    }
                }
            } else if (data.login) {
                obj.store(data);
                if (setting && typeof(setting.errorCallback) == "function") {
                    if (obj.captchaEnabled) {
                        obj.changeCaptcha(obj.captchaEnabledId);
                    }
                    obj.errorCount(data.count);
                    var result = {captcha: data.captcha, count: data.count,same:data.same};
                    setting.errorCallback(result);
                }
            } else {
                obj.store(data);
                var result = {same:data.same};
                if (setting && typeof(setting.unauthorized) == "function") {
                    setting.unauthorized(result);
                }
            }
        };

        this.errorCount = function (count) {
            obj.count = count;
        };

        this.store = function (data) {
            obj.dataPool.lt = data.lt;
            obj.dataPool.execution = data.execution;
        };

        this.refresh = function () {
            obj.dataPool = {};
        };

    };

});
