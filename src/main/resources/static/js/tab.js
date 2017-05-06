jQuery(document).ready(function() {
	jQuery('.tab a').on('click', function(e)  {
	    
		var currentAttrValue = jQuery(this).attr('href');
		
		// Show/Hide Tabs
        jQuery('.slide-content .slide_img ' + currentAttrValue).fadeIn(400).siblings().hide();
        
        // Change/remove current tab to active
        jQuery(this).addClass('active').siblings().removeClass('active');
        
        e.preventDefault();
	});
});