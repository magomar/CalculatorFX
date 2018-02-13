package calculatorfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:magomar@gmail.com">Mario Gómez</a>
 * Created on 30/03/2017.
 */
public class MainViewController implements Initializable {

    private final ObjectProperty<BigDecimal> operand1 = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> operand2 = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Operator> operation = new SimpleObjectProperty<>(null);
    private int decimalPos = 0;
    @FXML
    private TextField output;

    public void reset() {
        operand1.set(BigDecimal.ZERO);
        operand2.set(null);
        operation.set(null);
        decimalPos = 0;
    }

    public BigDecimal getOperand1() {
        return this.operand1.get();
    }

    public void setOperand1(final BigDecimal x) {
        this.operand1.set(x);

    }

    public ObjectProperty<BigDecimal> Operand1Property() {
        return this.operand1;
    }

    public BigDecimal getOperand2() {
        return this.operand2.get();
    }

    public void setOperand2(BigDecimal y) {
        this.operand2.set(y);
    }

    public ObjectProperty<BigDecimal> Operand2Property() {
        return this.operand2;
    }

    public Operator getOperator() {
        return this.operation.get();
    }

    public void setOperator(final Operator operation) {
        this.operation.set(operation);
    }

    public ObjectProperty<Operator> OperatorProperty() {
        return this.operation;
    }

    @FXML
    public void digit(final ActionEvent event) {
        final Button button = (Button) event.getSource();
        final Long digit = Long.parseLong(button.getText());
        final BigDecimal x = getOperand1();
        if (getOperand2() == null && getOperator() != null) {
            setOperand2(x);
            setOperand1(BigDecimal.valueOf(digit));
            this.decimalPos = 0;
        } else if (decimalPos > 0) {
            setOperand1(x.add(BigDecimal.valueOf(digit).divide(BigDecimal.valueOf(decimalPos))));
            decimalPos *= 10;
        } else {
            setOperand1(x.multiply(BigDecimal.valueOf(10)).add(BigDecimal.valueOf(digit)));
        }
        output.setText(NumberFormat.getInstance().format(getOperand1().doubleValue()));
    }


    @FXML
    void decimal(final ActionEvent event) {
        decimalPos = 10;
    }

    @FXML
    void operation(final ActionEvent event) {
        if (getOperand2() != null && getOperator() != null) {
            compute(event);
        }
        final Button button = (Button) event.getSource();
        Operator op = Operator.valueOf(button.getId());
        setOperator(op);
    }

    @FXML
    void clear(final ActionEvent event) {
        reset();
    }

    @FXML
    void compute(final ActionEvent event) {
        if (getOperator() == null) {
            setOperand2(null);
            return;
        }

        BigDecimal result;
        switch (getOperator()) {
            case ADD:
                result = getOperand2().add(getOperand1());
                break;
            case SUB:
                result = getOperand2().subtract(getOperand1());
                break;
            case MUL:
                result = getOperand2().multiply(getOperand1());
                break;
            case DIV:
                result = getOperand2().divide(getOperand1());
                break;
            default:
                throw new IllegalStateException();
        }

        setOperand1(result);
        setOperand2(null);
        setOperator(null);
        decimalPos = 0;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset();
        Operand1Property().addListener((ObservableValue<? extends BigDecimal> observable, BigDecimal oldValue, BigDecimal newValue) -> {
            output.setText(NumberFormat.getInstance().format(operand1.getValue().doubleValue()));
        });
    }


}
 
