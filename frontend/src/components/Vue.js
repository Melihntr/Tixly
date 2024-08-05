new Vue({
    el: "#app",
    data() {
      return {
        currentCardBackground: Math.floor(Math.random() * 25 + 1),
        cardName: "",
        cardNumber: "",
        cardMonth: "",
        cardYear: "",
        cardCvv: "",
        minCardYear: new Date().getFullYear(),
        amexCardMask: "#### ###### #####",
        otherCardMask: "#### #### #### ####",
        cardNumberTemp: "",
        isCardFlipped: false,
        focusElementStyle: null,
        isInputFocused: false
      };
    },
    mounted() {
      this.cardNumberTemp = this.otherCardMask;
      document.getElementById("cardNumber").focus();
    },
    computed: {
      getCardType() {
        let number = this.cardNumber;
        if (/^4/.test(number)) return "visa";
        if (/^(34|37)/.test(number)) return "amex";
        if (/^5[1-5]/.test(number)) return "mastercard";
        if (/^6011/.test(number)) return "discover";
        if (/^9792/.test(number)) return "troy";
        return "visa";
      },
      generateCardNumberMask() {
        return this.getCardType === "amex" ? this.amexCardMask : this.otherCardMask;
      },
      minCardMonth() {
        return this.cardYear === this.minCardYear ? new Date().getMonth() + 1 : 1;
      }
    },
    watch: {
      cardYear() {
        if (this.cardMonth < this.minCardMonth) {
          this.cardMonth = "";
        }
      }
    },
    methods: {
      flipCard(status) {
        this.isCardFlipped = status;
      },
      focusInput(e) {
        this.isInputFocused = true;
        let targetRef = e.target.dataset.ref;
        let target = this.$refs[targetRef];
        this.focusElementStyle = {
          width: `${target.offsetWidth}px`,
          height: `${target.offsetHeight}px`,
          transform: `translateX(${target.offsetLeft}px) translateY(${target.offsetTop}px)`
        }
      },
      blurInput() {
        setTimeout(() => {
          if (!this.isInputFocused) {
            this.focusElementStyle = null;
          }
        }, 300);
        this.isInputFocused = false;
      }
    }
  });
  