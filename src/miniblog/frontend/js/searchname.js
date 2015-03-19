// Search function
function searchname(name) {
    // Remove 10 latest post line
    $("#latestpost").remove();
    // Rename "Home" to "Search" 
    $("#page").text("Search");
        
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/miniblog/v1/users/search/name="+name,
                data: {"name": name},
                success: function(response, status, xhr) {
                    if (status == "success") {
                        $.each(response['data'], function(key, value) {
                        $("#post").append("\
					<div class='col-xs-12 col-sm-12 col-md-12'>\
						<div class='col-xs-2 col-sm-2 col-md-2 inline-block'>\
							<a href='#'><img src='image/avatar8.jpg' class='img-responsive' alt='Cinque Terre'></a>\
						</div>\
						<div class='col-xs-10 col-sm-10 col-md-10 inline-block blog-main'>\
							<div class=' justify'>\
								<span><a href='#' class='remove-underline'>"+value['username']+"</a></span>\
							</div>\
							<div class=' blog-post-meta justify'><small>Joined date: "+value['created_at_fm']+"</small></div>\
                            <div class=' blog-post-meta justify'>Firstname: "+value['firstname']+"</div>\
                            <div class=' blog-post-meta justify'>Lastname: "+value['lastname']+"</div>\
						</div>\
					</div>\
				</div>"
                                              )
                        });
                    }
                }
            })
        }      