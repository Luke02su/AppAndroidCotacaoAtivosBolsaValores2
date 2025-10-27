# Cotações de Ativos da Bolsa de Valores 2.0 📈💰  

[![Kotlin](https://img.shields.io/badge/Linguagem-Kotlin-orange?logo=kotlin)](https://kotlinlang.org/)  
[![Android Studio](https://img.shields.io/badge/IDE-Android_Studio-brightgreen?logo=android-studio)](https://developer.android.com/studio)  
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)  

---

## 📌 Projeto
Este aplicativo Android, desenvolvido em **Kotlin** e **Compose**, permite consultar **cotações de ativos da B3 e de bolsas internacionais**, consumindo dados em tempo real da **API [brapi.dev](https://brapi.dev/)**.

O app possui um **design moderno e responsivo**, exibindo informações detalhadas como preço atual, moeda, fechamento anterior (moeda), variação diária (porcentagem e moeda) e intervalo do dia (porcentagem e moeda) intervalo de 52 semanas (porcentagem e moeda). É ideal para **investidores e entusiastas do mercado financeiro** que desejam acompanhar ativos de forma **prática e diária** pelo celular.

Eu, particularmentre, pretendo utilizá-lo diariamente para a realização de avaliação de ativos que possuo na minha **carteira de investimentos**. Portanto, caso venha a utilizá-lo de maneira **frequente**, peço, por gentileza, que crie uma conta na **brapi** e gere seu próprio **token** e o insira na variável de mesmo nome, uma vez que é limitada a **15.000** requisiçoes **GET** por mês. 

E, para se obter mais informações ao realizar a consulta, como **dividend yield**, dentre outras, faz-se necessário assinar outros planos pagos.

Este aplicativo foi feito a partir de uma **engenharia reversa e complementar** desta versão desenvolvida com XML: https://github.com/Luke02su/AppAndroidCotacaoAtivosBolsaValores, adicionando opção de favoritar e exibir lista de ativos favoritos, facilitando, assim, o acesso daqueles que se deseja analisar, sejam eles alvo de compra ou venda.

---

## 🎨 Layout do App

### Tela Principal
- **Campo de entrada (OutlinedTextField):** digite o ticker do ativo (até 6 caracteres, automaticamente em maiúsculas).  
- **Logo do ativo (AsynImage):** carregada automaticamente via URL (suporte a SVG usando Coil).  
- **Caixa de informações (Column):** apresenta os principais dados do ativo pesquisado.

### Informações exibidas
- Nome curto do ativo  
- Moeda de negociação  
- Preço atual  
- Fechamento anterior (preço)  
- Variação em moeda e percentual do dia (**verde positivo, vermelho negativo**)  
- Intervalo diário de preço (mínimo e máximo) 
- Intervalo do preço das últimas 52 semanas (mínimo e máximo) 

---

## 📱 Prints da Tela

<p align="center">
   <img width="300" height="600" alt="Tela 1" src="https://github.com/user-attachments/assets/ae69663d-cdee-4d96-b72e-655674248594" />
   <img width="300" height="600" alt="Tela 2" src="https://github.com/user-attachments/assets/b5720e45-dae4-4c6a-955e-e2bd621790a2" />
   <img width="300" height="600" alt="Tela 3" src="https://github.com/user-attachments/assets/f6876a76-e821-4a28-a196-1b11e81fb884" />
</p>

## ✨ Funcionalidades
| Funcionalidade        | Descrição |
|----------------------|-----------|
| Consulta de Ativos     | Busca informações em tempo real através da API brapi.dev |
| Exibição de Logo       | Carregamento automático do logo do ativo (SVG suportado via Coil) |
| Informações do Ativo   | Nome, moeda, preço, variação diária e intervalos |
| Campo de Entrada       | Aceita apenas tickers (máx. 6 caracteres, maiúsculos) |
| Variação de Preço      | Cor verde para positivo e vermelho para negativo |
| Favoritar Ativo        | Opção de favoritar ativo clicando no ícone de coração |
| Listar Ativos Favorios | Lista os ativos favoritos: logo, ticket e último preço, sendo acessíveis clicando no ícone de três traços |
| UI Responsiva          | Layout adaptado para diferentes tamanhos de tela |
| Edge-to-Edge           | Uso completo da tela, status bar transparente |
| Toast                  | Informa ao usuário para digitar corretamente o ticker e informa quando está sem conexão com a rede
| rememberScrollState    | Permite rolagem caso a tela ultrapasse o limite visível |

---

## 🔄 Fluxo de Uso
1. Digitar o **ticker** do ativo no campo de entrada.  
2. O app consome a **API brapi.dev** e retorna os dados em tempo real.  
3. As informações são exibidas em um **layout limpo e responsivo**.  
4. A logo do ativo é carregada automaticamente no topo.
5. Ao lado esuqerdo há ícone de coração para favoritar ativo.
6. Ao lado direito há ícone de três traços para exibir lista de ativos favoritos.

### Exemplos de Tickers para teste sem utilização de token:
- PETR4 (Petrobras PN)  
- VALE3 (Vale ON)  
- AAPL (Apple - Nasdaq)  
- MSFT (Microsoft - Nasdaq)  

---

## 🛠 Tecnologias Utilizadas
- **Linguagem:** Kotlin  
- **IDE:** Android Studio  
- **API:** [brapi.dev](https://brapi.dev/)  
- **UI Components:** ConstraintLayout, LinearLayout, ScrollView, EditText, TextView, ImageView  
- **Biblioteca de Imagens:** [Coil](https://coil-kt.github.io/coil/) (com suporte a SVG via `SvgDecoder`)  
- **Design:**  
  - Fundo: Azul escuro `#1B263B`  
  - Texto principal: Cinza quase preto `#212121`  
  - Caixa de informações: Drawable estilizado em tons claros  
  - Campos com bordas arredondadas e espaçamento consistente  

---

## 🔧 Boas Práticas Implementadas
- Modularização do código para facilitar manutenção  
- Reutilização de estilos XML para TextView e EditText  
- Input validation: só aceita tickers válidos  
- Layout edge-to-edge para design moderno  
- Responsivo e adaptável a diferentes tamanhos de tela  

---

## 👨‍💻 Autor
**Lucas Samuel Dias**  
- Desenvolvedor Kotlin Android 
- Focado em integração de APIs, UI/UX e aplicativos financeiros  

---

## 📜 Licença
Este projeto está licenciado sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
