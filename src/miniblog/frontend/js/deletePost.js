// Delete post function  
    function deletePost(id) {
        var opt = confirm("Are you sure that you want to delete this post?");
        
        if (opt == true) {
            $.ajax({
                type: "DELETE",
                beforeSend: function(request) {
                    request.setRequestHeader("access_token", getCookie('token'));    
                },
                url: $apiadd+"posts/"+id+"/delete",
                statusCode: {
                    9001: function() {
                        alert("Error.");
                        window.location.reload();
                    },
                    2505: function() {
                        alert("Can not delete post that does not belong to you.");
                        // set null and send to userpost.html page in order to get userid from cookie at userpost.html page
                        window.name = "null";
                        window.location = 'userpost.html';
                    },
                    1003: function() {
                        alert("Token is invalid");
                        window.location = 'welcome.html';
                    },
                    2504: function() {
                        alert("Token has expired. Please login");
                        window.location = 'welcome.html';
                    },
                    9002: function() {
                        alert("Token is missing. Please login.");
                        window.location = 'welcome.html';
                    },
                    2504: function() {
                        alert("Post is not existed.");
                        window.location.reload();
                    }
                },
                success: function(response, status, xhr) {
                    alert("Post is deleted success");
                    // set null and send to userpost.html page in order to get userid from cookie at userpost.html page
                    window.name = "null";
                    window.location = 'userpost.html';
                }
            })
        }
        else {
            return;
        }
        
    
    }