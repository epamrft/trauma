/* Button listener utility class */
var buttonActions = Class.create({


  initialize : function(comms) {
    this.comms=comms;
    $('saveBtn').observe('click', this.saveInfo);
  	$('cancelBtn').observe('click', this.cancelProcess);
  	$('editBtn').observe('click', this.editInfo);
  	$('delBtn').observe('click', this.deleteMarker);
  	self = this;
  },

  /* Sending marker to backend */
  saveInfo : function()
  			{
  			self.comms.sendMarker();
  			},
  /* Cancel addig process */
  cancelProcess : function()
  			{
  			self.comms.map.service.effects.hide($('descbox'));
  			self.comms.map.tmpMarker.setMap(null);
    		},
  /* Deleting marker from backend */
  deleteMarker : function()
  			{
    		self.comms.deleteMarker();
    		},
  /* Edit marker on backend */
  editInfo : function()
  			{
    		self.comms.updateMarker();  
    		}

  });