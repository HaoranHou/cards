package objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
public final class Card {
    @NonNull
    private final Suit suit;
    @NonNull
    private final FaceValue faceValue;

    @Override
    public String toString() {
        return String.valueOf(faceValue) + " of " + suit;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof Card)) return false;
        Card newCard = (Card) object;
        return new EqualsBuilder().append(suit, newCard.suit).append(faceValue, newCard.faceValue).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(suit).append(faceValue).toHashCode();
    }
}
