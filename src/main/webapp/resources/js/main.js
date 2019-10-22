(function ($) {
    "use strict";


    /*==================================================================
    [ Focus input ]*/
    $('.input100').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() != "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })
    })


    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(){
        var check = true;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check=false;
            }
        }

        return check;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }

    /*==================================================================
    [ Show pass ]*/
    var showPass = 0;
    $('.btn-show-pass').on('click', function(){
        if(showPass == 0) {
            $(this).next('input').attr('type','text');
            $(this).addClass('active');
            showPass = 1;
        }
        else {
            $(this).next('input').attr('type','password');
            $(this).removeClass('active');
            showPass = 0;
        }

    });


})(jQuery);

/*==================================================================
[ searchable dropdown ]*/

function searchFunction(e) {
	document.getElementById("myDropdown").classList.toggle("show");
//	$('#myDropdown').show();
	e.preventDefault();
}

function filterFunction() {
	var input, filter, ul, li, a, i;
	input = document.getElementById("myInput");
	filter = input.value.toUpperCase();
	div = document.getElementById("myDropdown");
	a = div.getElementsByTagName("a");
	for (i = 0; i < a.length; i++) {
		txtValue = a[i].textContent || a[i].innerText;
		if (txtValue.toUpperCase().indexOf(filter) > -1) {
		a[i].style.display = "";
	} else {
      a[i].style.display = "none";
    }
  }
}

function sendUserId(userid) {
	$('#shareduserid').val(userid);
}

/*==================================================================
[ show modal ]*/

function fileUploadModal() {
	$('#fileUploadModal').modal();
}

function addFolderModal() {
	$('#addFolderModal').modal();
}

function renameFolderModal(folderid) {
	$('#folderid').val(folderid);
	$('#renameFolderModal').modal();
}

function deleteFolderModal(folderid) {
	$('#deletefolderid').val(folderid);
	$('#deleteFolderModal').modal();
}

function renameFileModal(fileid) {
	console.log(fileid);
	$('#renamefileid').val(fileid);
	$('#renameFileModal').modal();
}

function deleteFileModal(fileid) {
	$('#deletefileid').val(fileid);
	$('#deleteFileModal').modal();
}

function shareFileModal(fileid) {
	$('#sharefileid').val(fileid);
	$('#shareFileModal').modal();
}

/*==================================================================
[ form submission ]*/

function login() {
	document.forms[0].submit();
}

function addFolder() {
	document.forms[0].submit();
}

function renameFolder() {
	document.forms[1].submit();	
}

function deleteFolder() {
	document.forms[2].submit();
}

function uploadFile() {
	document.forms[0].submit();
}

function renameFile() {
	document.forms[1].submit();
}

function deleteFile() {
	document.forms[2].submit();
}

function shareFile() {
	document.forms[3].submit();
}