$(function () {
    // Datatable - master
    $('#master-data').DataTable({
        "language": {
            "lengthMenu": "_MENU_ Data per halaman",
            "info": "Menampilkan _START_ sampai _END_ dari _TOTAL_ data",
            "zeroRecords": "Tidak ada data.",
            "search": "Pencarian :",
            "infoFiltered": "(tersaring dari total _MAX_ data)",
            "paginate": {
                "previous": "Sebelumnya",
                "next": "Selanjutnya"
            }
        },
    });

    $('#master-data2').DataTable({
        "language": {
            "lengthMenu": "_MENU_ Data per halaman",
            "info": "Menampilkan _START_ sampai _END_ dari _TOTAL_ data",
            "zeroRecords": "Tidak ada data.",
            "search": "Pencarian :",
            "infoFiltered": "(tersaring dari total _MAX_ data)",
            "paginate": {
                "previous": "Sebelumnya",
                "next": "Selanjutnya"
            }
        },
    });

    // Alert Auto Fade
    setTimeout(function () {
        let alert = $('.alert');
        if(!alert.hasClass('validationAlert')) {
            $(".alert").slideUp(500);
        }
    }, 3000);

    $('.zoom-image').zoom();

    $('body').on('keypress', 'input[type=number][maxlength]', function(event){
        var key = event.keyCode || event.charCode;
        var charcodestring = String.fromCharCode(event.which);
        var txtVal = $(this).val();
        var maxlength = $(this).attr('maxlength');
        var regex = new RegExp('^[0-9]+$');
        // 8 = backspace 46 = Del 13 = Enter 39 = Left 37 = right Tab = 9
        if( key == 8 || key == 46 || key == 13 || key == 37 || key == 39 || key == 9 ){
            return true;
        }
        // maxlength allready reached
        if(txtVal.length==maxlength){
            event.preventDefault();
            return false;
        }
        // pressed key have to be a number
        if( !regex.test(charcodestring) ){
            event.preventDefault();
            return false;
        }
        return true;
    });

    $('body').on('paste', 'input[type=number][maxlength]', function(event) {
        //catch copy and paste
        var ref = $(this);
        var regex = new RegExp('^[0-9]+$');
        var maxlength = ref.attr('maxlength');
        var clipboardData = event.originalEvent.clipboardData.getData('text');
        var txtVal = ref.val();//current value
        var filteredString = '';
        var combined_input = txtVal + clipboardData;//dont forget old data

        for (var i = 0; i < combined_input.length; i++) {
            if( filteredString.length < maxlength ){
                if( regex.test(combined_input[i]) ){
                    filteredString += combined_input[i];
                }
            }
        }
        setTimeout(function(){
            ref.val('').val(filteredString)
        },100);
    });
})

function getNumber(str) {
    return parseInt(str.replace(/[^0-9]/g, ''));
}

function txtMoney(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function minmax(value, min, max)
{
    if(parseInt(value) < min || isNaN(parseInt(value)))
        return min;
    else if(parseInt(value) > max)
        return max;
    else return value;
}

const currencyFormat = new Intl.NumberFormat('id-ID', {
    style: 'currency',
    currency: 'IDR',
    minimumFractionDigits: 0
})
