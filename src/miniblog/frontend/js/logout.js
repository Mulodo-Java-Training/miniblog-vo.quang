// Function logout
    function logout() {
                var opt = confirm("Are you sure that you want to logout?");

                if (opt == true) {
                    $.ajax({
                        type: "POST",
                        beforeSend: function(request) {
                            request.setRequestHeader("access_token", getCookie('token'));
                        },
                        url: "http://localhost:8080/miniblog/v1/users/logout",
                        statusCode: {
                            9002: function() {
                                alert('Missing token. Please login!');
                                window.location = 'welcome.html';
                                return;
                            },
                            9001: function() {
                                alert('Error');
                                window.location = 'welcome.html';
                                return;
                            }
                        },
                        success: function(response, status, xhr) {
                            if (status != 'success') {
                                alert("Unknown...");
                                window.location.reload();
                                return;
                            }
                            else {
                                var now = new Date();
                                document.cookie = "token=;expires=" + now.toGMTString() + ";";
                                document.cookie = 'userid=;expires=' + now.toGMTString() + ';';
                                document.cookie = 'username=;expires=' + now.toGMTString() + ';';
                                window.location = 'welcome.html';
                            }
                        }
                    })	
                }
                else {
                     return;
                    }
    }