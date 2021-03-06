var prosseguir = true;

String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
};

function iniciarToastr () {
   toastr.options = {
       "closeButton": false,
       "debug": false,
       "newestOnTop": false,
       "progressBar": false,
       "positionClass": "toast-bottom-right",
       "preventDuplicates": false,
       "onclick": null,
       "showDuration": "300",
       "hideDuration": "1000",
       "timeOut": "5000",
       "extendedTimeOut": "1000",
       "showEasing": "swing",
       "hideEasing": "linear",
       "showMethod": "fadeIn",
       "hideMethod": "fadeOut"
   };
};

function formatarValor (valor) {
    if (valor != null) {            

        valor = String(valor);
        var temDecimal = valor.indexOf(".") > -1;
        var inteiros = temDecimal ? valor.split(".")[0] : valor; 
        var decimais = temDecimal ? valor.split(".")[1] : "";                       

        var contador = 0;
        var novoInteiro = "";

        for (var i = inteiros.length; i > 0; i--) {
            if (contador != 0 && contador % 3 == 0 && i > 0) {
                novoInteiro += ".";
                contador = 0;
            }

            novoInteiro += inteiros[i - 1];                                    
            contador++;
        }

        inteiros = "";

        for (var i = novoInteiro.length; i > 0; i--) {
            inteiros += novoInteiro[i - 1];
        }            

        if (temDecimal) {
            if(decimais.length == 1) decimais += "0";

            decimais = "," + decimais;
        } else {
            decimais += ",00";
        }

        return "R$ " + inteiros + decimais;
    }

    return "R$ 0,00";
}; 

function retornarDecimal (valor) {
    if (valor != null) {

        if (valor.indexOf("$") > -1) valor = valor.replace("R$", "");
        if (valor.indexOf(".") > -1) valor = valor.replace(".", "");
        if (valor.indexOf(",") > -1) valor = valor.replace(",", ".");

        return parseFloat(valor);
    }

    return 0;
};

function validarCampos (valor, mensagem, campo) {
    if (valor <= 0 || valor == null) {
        toastr.warning(mensagem, "Alerta!");
        $("#" + campo).addClass("has-warning");
        prosseguir = false;
    } else {
        $("#" + campo).removeClass("has-warning");
        prosseguir = true;
    } 
};

function dispararResposta () {
    var sucesso = $("#sucesso").val();
    var mensagem = $("#mensagem").val();
    var campo = $("#campo").val();

    if (sucesso === "false") {
        $("#" + campo).addClass("has-error");
        toastr.error(mensagem, 'Erro!');
    } else if (sucesso === "true") {
        toastr.success(mensagem, 'Sucesso!');
    }
};
