function refreshReport()
{
	alert("in refreshReport");
    var updateUserRoute = #{jsRoute @DonationController.renderReport() /};
    $.ajax({
      url: updateUserRoute.url(),
      type: updateUserRoute.method
    });
};



 