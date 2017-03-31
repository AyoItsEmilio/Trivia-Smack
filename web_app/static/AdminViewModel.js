$(document).ready(function(){
function AdminViewModel() {
    var self = this;
    self.username = ko.observable("");
    self.password = ko.observable("");
    self.loginURI = "/api/login";

    self.ajax = function(uri, method, data) {
        var request = {
            url: uri,
            type: method,
            contentType: "application/json",
            accepts: "application/json",
            cache: false,
            dataType: "json",
            data: JSON.stringify(data),
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", 
                    "Basic " + btoa(self.username() + ":" + self.password()));
            },
            error: function(jqXHR) {
                console.log("ajax error " + jqXHR.status);
            }
        };
        return $.ajax(request);
    };

    self.login = function() {
        self.ajax(self.loginURI, "GET").done(function(data) {
            console.log("success");
        }).fail(function(jqXHR) {
            console.log("failure");
        });
    };
}
ko.applyBindings(new AdminViewModel(), $("#admin")[0]);
});
