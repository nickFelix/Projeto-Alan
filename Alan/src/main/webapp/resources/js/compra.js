var carrinhoCompra = [],
prosseguir = true;
    
$(document).ready(function () {
    
    var formatarValor = function (valor) {
        if (valor != null) {
            valor = String(valor);
            if (valor.indexOf(".") > -1) {
                var arr = valor.split(".");
                if(arr[1].length == 1) valor += "0";
                
                valor = valor.replace(".", ",");
            } else {
                valor += ",00";
            }
            
            return "R$ " + valor;
        }
        
        return "R$ 0,00";
    };
    
    var retornarDecimal = function(valor) {
        if (valor != null) {
            if (valor.indexOf("$") > -1) valor = valor.replace("R$", "");
            if (valor.indexOf(",") > -1) valor = valor.replace(",", ".");
            
            return parseFloat(valor);
        }
        
        return 0;
    };
    
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
    
    $(document).on("click", "#btnSalvar", function () {
       $("#filialId").val($("codigoFilial").val());
       $("#jsonItens").val(JSON.stringify(carrinhoCompra));
    });

    $(document).on("click", "button[id='btnIncluir']", function () {

        $("#itens").removeClass("hidden");

        var codigoFilial = $("#codigoFilial").val();
        var codigoProduto = $("#codigoProduto").val();
        var nomeProduto = $("#codigoProduto option:selected").text();
        var quantidade = $("#Quantidade").val();
        var valorUnitario = $("#valorUnitario").val();
        var valorTotal = $("#valorTotal").val();
        
        validarCampos(codigoFilial, "Informe uma Filial.", "codigoFilial");
        validarCampos(codigoProduto, "Informe o codigo do produto de maneira correta", "codigoProduto");
        validarCampos(quantidade, "Informe a quantidade do produto de maneira correta", "Quantidade");
   
        if (prosseguir) {
            
            var compra = {
                codigoProduto: codigoProduto,
                nomeProduto: nomeProduto,
                quantidade: parseInt(quantidade),
                valorUnitario: retornarDecimal(valorUnitario),
                valorTotal: retornarDecimal(valorTotal)
            };

            carrinhoCompra.push(compra);
            preencherTabela(compra);
            atualizarTotal();
            limparCampos();
        }
    });
    
    $(document).on("focus", "#Quantidade", function () {
       $(this).val(""); 
    });
    
    $(document).on("blur", "#Quantidade", function () {
        var quantidade = $("#Quantidade").val();
        
        if(quantidade != 0 || quantidade != null) {
            var valorProduto = $("#codigoProduto option:selected").data("valor");   
            var valorTotal = quantidade * valorProduto;
            
            $("#valorTotal").val(formatarValor(valorTotal));
        }
    });
    
    $(document).on("change", "#codigoProduto", function () {
        var valorProduto = $("#codigoProduto option:selected").data("valor");  
        
        $("#valorUnitario").val(formatarValor(valorProduto));
        $("#Quantidade").focus();
    });
    
    $(document).on("click", "button[name='btnRemoverItem']", function() {
        var valorTotalCompra = retornarDecimal($("#valorTotalCompra").text());
        var valorTotalItem = retornarDecimal($(this).parent().prev().text());
        var subtracao = valorTotalCompra - valorTotalItem;

        $(this).parent().parent().remove();
        $("#valorTotalCompra").text(formatarValor(subtracao));
    });
    
    var validarCampos = function (valor, mensagem, campo) {
        if (valor == 0 || valor == null) {
            toastr.warning(mensagem, "Alerta!");
            $("#" + campo).addClass("has-warning");
            prosseguir = false;
        } else {
            $("#" + campo).removeClass("has-warning");
            prosseguir = true;
        } 
    };
    
    var limparCampos = function () {
        $("#codigoFilial").val(0);
        $("#codigoProduto").val(0);
        $("#Quantidade").val(0);
        $("#valorUnitario").val("RS 0,00");
        $("#valorTotal").val("RS 0,00");
    };

    var preencherTabela = function (compra) {
        var tbody = $("#compras tbody");
        var htmlStr = "<tr><td>" + compra.codigoProduto + 
                      "</td><td>" + compra.nomeProduto + 
                      "</td><td>" + compra.quantidade + 
                      "</td><td>" + formatarValor(compra.valorUnitario) + 
                      "</td><td>" + formatarValor(compra.valorTotal) + 
                      "</td><td><button type='button' class='btn btn-primary btn-danger btn-xs' name='btnRemoverItem'>Remover</button></td></tr>";

        tbody.append(htmlStr);
    };
    
    var atualizarTotal = function () {
        var total = 0;

        for(var i = 0; i < carrinhoCompra.length; i++){        
            total += carrinhoCompra[i].valorTotal;
        }
        
        $("#valorTotalCompra").text(formatarValor(total));
    };

});
