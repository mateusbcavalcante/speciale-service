package br.com.a2dm.spdm.utils;

import java.math.BigInteger;

import br.com.a2dm.spdm.entity.PedidoProduto;
import br.com.a2dm.spdm.entity.Produto;

public final class RelatorioPedidoCalculoUtil {

	private static final int UNIDADES_CAIXA_INTEGRAL = 30;
	private static final int UNIDADES_CAIXA_PADRAO = 12;

	private RelatorioPedidoCalculoUtil() {
	}

	public static long calcularCaixas(PedidoProduto pedidoProduto) {
		if (pedidoProduto == null || pedidoProduto.getQtdSolicitada() == null) {
			return 0;
		}
		if (isVendaPorUnidade(pedidoProduto.getUnidade())) {
			return 0;
		}
		int unidadesPorCaixa = isProdutoIntegral(pedidoProduto.getProduto()) ? UNIDADES_CAIXA_INTEGRAL
				: UNIDADES_CAIXA_PADRAO;
		return pedidoProduto.getQtdSolicitada().longValue() / unidadesPorCaixa;
	}

	public static long calcularPacotes(PedidoProduto pedidoProduto) {
		if (pedidoProduto == null || pedidoProduto.getQtdSolicitada() == null) {
			return 0;
		}
		if (isVendaPorUnidade(pedidoProduto.getUnidade())) {
			return pedidoProduto.getQtdSolicitada().longValue();
		}
		int unidadesPorCaixa = isProdutoIntegral(pedidoProduto.getProduto()) ? UNIDADES_CAIXA_INTEGRAL
				: UNIDADES_CAIXA_PADRAO;
		return pedidoProduto.getQtdSolicitada().longValue() % unidadesPorCaixa;
	}

	private static boolean isVendaPorUnidade(String unidade) {
		return unidade != null && unidade.trim().equalsIgnoreCase("UND");
	}

	private static boolean isProdutoIntegral(Produto produto) {
		return produto != null && produto.isIntegral();
	}

	public static String resolverUnidade(PedidoProduto pedidoProduto) {
		if (pedidoProduto.getUnidade() != null && !pedidoProduto.getUnidade().trim().isEmpty()) {
			return pedidoProduto.getUnidade();
		}
		if (pedidoProduto.getProduto() != null && pedidoProduto.getProduto().getUnidade() != null) {
			return pedidoProduto.getProduto().getUnidade();
		}
		return "";
	}

	public static BigInteger somarQuantidade(BigInteger acumulado, BigInteger valor) {
		if (valor == null) {
			return acumulado;
		}
		if (acumulado == null) {
			return valor;
		}
		return acumulado.add(valor);
	}
}
