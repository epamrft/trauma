var Effects = Class.create({

	hide : function(elementId) {
		new Effect.Fade(elementId);
	},

	show : function(elementId, finishFunction) {
		new Effect.Appear(elementId, {
			afterFinish : function(obj) {
				finishFunction();
			}
		});
	},

	focus : function(elementId) {
		$(elementId).focus();
	},

});