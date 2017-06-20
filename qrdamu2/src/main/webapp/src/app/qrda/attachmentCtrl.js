function AttachmentCtrl(FileUploader, $scope, toastr) {
	var that = this ;
	
	 var uploader = $scope.uploader = new FileUploader({
         url: 'qrda/uploadQrdaZip'
     });

     // FILTERS

     uploader.filters.push({
         name: 'customFilter',
         fn: function(item /*{File|FileLikeObject}*/, options) {
             return this.queue.length < 10;
         }
     });

     uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
       //  console.info('onWhenAddingFileFailed', item, filter, options);
     };
     uploader.onAfterAddingFile = function(fileItem) {
     	//fileItem.upload();
        // console.info('onAfterAddingFile', fileItem);
     };
     uploader.onAfterAddingAll = function(addedFileItems) {
     	
       //  console.info('onAfterAddingAll', addedFileItems);
     };
     uploader.onBeforeUploadItem = function(item) {
        // console.info('onBeforeUploadItem', item);
     };
     uploader.onProgressItem = function(fileItem, progress) {
       //  console.info('onProgressItem', fileItem, progress);
     };
     uploader.onProgressAll = function(progress) {
        // console.info('onProgressAll', progress);
     };
     uploader.onSuccessItem = function(fileItem, response, status, headers) {
         //console.info('onSuccessItem', fileItem, response, status, headers);
     };
     uploader.onErrorItem = function(fileItem, response, status, headers) {
        // console.info('onErrorItem', fileItem, response, status, headers);
     };
     uploader.onCancelItem = function(fileItem, response, status, headers) {
        // console.info('onCancelItem', fileItem, response, status, headers);
     };
     $scope.IDs = [];
    
     uploader.onCompleteAll = function() {
    	 $scope.hide();
    	 toastr.success("CATEGORY 1 Files Imported Successfully");
     };

     
}

angular.module('qrda').controller('AttachmentCtrl', AttachmentCtrl);
