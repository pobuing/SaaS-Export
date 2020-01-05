function setSidebarActive(th) {
    $(th).parent("li").siblings().removeClass("active");
    $(th).parent("li").addClass("active");
}

function getCheckIds() {
    var size = $("input:checkbox:checked").length;
    var ids;
    for (let i = 0; i < size; i++) {
        ids = ids + $('input[type=checkbox]:checked').val();
        if (i != size - 1) {
            //最后一条
            ids += ',';
        }
    }
    return ids;
}

function getCheckId() {
    var size = $("input:checkbox:checked").length;
    if (size != 1) {
        return;
    } else {
        return $('input[type=checkbox]:checked').val();
    }
}



function formSubmit(url, sTarget) {
    document.forms[0].target = sTarget
    document.forms[0].action = url;
    document.forms[0].submit();
    return true;
}