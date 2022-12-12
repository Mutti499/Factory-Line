import java.util.ArrayList;
import java.util.NoSuchElementException;

class FactoryImpl implements Factory {
    private Holder first;
    private Holder last;
    private int size = 0;

    // ADDING WITHOUT INDEX
    // public void brokenAdd(Product product) {
    // Holder newEl = new Holder(last,product,null);

    // System.out.println(newEl + " will be added added.");

    // last.setNextHolder(newEl);
    // last = newEl;
    // System.out.println(newEl + " has added.");
    // }
    // END OF ADDING WITHOUT INDEX

    public void addFirst(Product product) {
        if (size == 0) {
            this.first = new Holder(null, product, null);
            size++;

        } else {
            if (first == null && last != null) {
                this.first = new Holder(null, product, last);
                size++;
            } else if (first != null && last == null) {
                Holder newEl = new Holder(null, product, first);
                first.setPreviousHolder(newEl);
                first = newEl;
                last = first.getNextHolder();
                size++;
            }

            else {
                Holder newEl = new Holder(null, product, first);
                first.setPreviousHolder(newEl);
                first = newEl;
                size++;
            }

        }
    }

    @Override
    public void add(int index, Product product) throws IndexOutOfBoundsException {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }

        else {
            if (index == 0) {
                addFirst(product);
            } else if (index == size) {
                addLast(product);
            } else {
                Holder current = first;

                for (int i = 0; i < index - 1; i++) {
                    current = current.getNextHolder();
                }
                Holder newEl = new Holder(current, product, current.getNextHolder());
                current.setNextHolder(newEl);
                newEl.getNextHolder().setPreviousHolder(newEl);
                size++;
            }

        }

    }

    @Override
    public void addLast(Product product) {
        if (size == 0) {
            this.last = new Holder(null, product, null);
            size++;

        } else {
            Holder current = first;

            while (current.getNextHolder() != null) {
                current = current.getNextHolder();
            }

            this.last = new Holder(null, product, null);
            last.setPreviousHolder(current);
            current.setNextHolder(last);
            size++;
        }
    }

    @Override
    public Product removeFirst() throws NoSuchElementException {

        if (size == 0) {
            return null;
        } else if (size == 1) {
            if (first != null) {
                Product toBeReturn = first.getProduct();

                first = null;
                size--;
                print();
                return toBeReturn;
            } else {
                Product toBeReturn = last.getProduct();

                last = null;
                size--;
                print();
                return toBeReturn;
            }

        } else {
            Product toBeReturn = first.getProduct();
            Holder second = first.getNextHolder();
            second.setPreviousHolder(null);
            first.setNextHolder(null);
            first = second;
            size--;
            print();
            return toBeReturn;

        }

    }

    @Override
    public Product removeLast() throws NoSuchElementException {

        if (size == 0) {
            return null;
        } else if (size == 1) {
            if (last != null) {
                Product toBeReturn = last.getProduct();

                last = null;
                size--;
                print();
                return toBeReturn;
            } else {
                Product toBeReturn = first.getProduct();

                first = null;
                size--;
                print();
                return toBeReturn;

            }

        } else if (size == 2 && first != null && last != null) {
            Product toBeReturn = last.getProduct();

            last.setPreviousHolder(null);
            first.setNextHolder(null);
            size--;
            last = null;
            return toBeReturn;
        } else {
            Product toBeReturn = last.getProduct();
            Holder oneLast = last.getPreviousHolder();
            oneLast.setNextHolder(null);
            last.setPreviousHolder(null);
            last = oneLast;
            size--;
            print();
            return toBeReturn;

        }

    }

    @Override
    public Product find(int id) throws NoSuchElementException {
        Holder current = first;
        while (current.getNextHolder() != null) {
            if (current.getProduct().getId() == id) {
                // System.out.println(current.getProduct());
                return current.getProduct();
            } else {
                current = current.getNextHolder();
            }

        }
        if (current.getProduct().getId() == id) {
            // System.out.println(current.getProduct());
            return current.getProduct();
        } else {
            // System.out.println("Product not found.");
            return null;
        }

    }

    @Override
    public Product update(int id, Integer value) throws NoSuchElementException {
        Product ProductToBeUpdated = find(id);

        if (ProductToBeUpdated != null) {
            Product oldProduct = new Product(id, ProductToBeUpdated.getValue());

            ProductToBeUpdated.setValue(value);

            return oldProduct;

        } else {
            return null;

        }
    }

    @Override
    public Product get(int index) throws IndexOutOfBoundsException {
        if (index >= size) {
            return null;
        } else {
            Holder current = first;
            for (int i = 0; i < index; i++) {
                current = current.getNextHolder();

            }
            return current.getProduct();
        }

    }

    @Override
    public Product removeIndex(int index) throws IndexOutOfBoundsException {
        Product removed = null;
        if (index == 0) {
            removed = removeFirst();
        } else if (index == size - 1) {
            removed = removeLast();
        } else if (index < 0 || index >= size) {
        } else {

            Holder current = first;
            for (int i = 0; i < index; i++) {
                current = current.getNextHolder();

            }

            Holder before = current.getPreviousHolder();
            Holder after = current.getNextHolder();

            before.setNextHolder(after);
            after.setPreviousHolder(before);
            size--;
            return current.getProduct();
        }
        return removed;
    }

    @Override
    public Product removeProduct(int value) throws NoSuchElementException {
        Holder current = first;
        int index = 0;
        while (current.getNextHolder() != null) {
            if (current.getProduct().getValue() == value) {
                Product removed = removeIndex(index);
                return removed;
            } else {
                current = current.getNextHolder();
                index++;
            }

        }
        if (current.getProduct().getValue() == value) {
            Product removed = removeIndex(index);
            return removed;
        } else {
            return null;
        }
    }

    @Override
    public int filterDuplicates() {
        int removedSize = 0;

        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<Integer> values = new ArrayList<Integer>();

        Holder current = first;
        for (int i = 0; i < size; i++) {
            ids.add(i, current.getProduct().getId());
            values.add(i, current.getProduct().getValue());
            current = current.getNextHolder();
        }
        for (int i = 0; i < size; i++) {

            for (int j = i + 1; j < size; j++) {


                if (values.get(i) == values.get(j) && j == size - 1) {
                    removeLast();
                    removedSize++;
                }

                else if (values.get(i) == values.get(j)) {
                    Holder toRemove = first;
                    for (int a = 0; a < j; a++) {
                        toRemove = toRemove.getNextHolder();
                    }
                    Holder before = toRemove.getPreviousHolder();
                    Holder after = toRemove.getNextHolder();

                    before.setNextHolder(after);
                    after.setPreviousHolder(before);
                    size--;

                    ids.remove(j);
                    values.remove(j);

                    removedSize++;
                }
            }

        }

        return removedSize;
    }

    @Override
    public void reverse() {
        if (size != 1) {
            if (size == 2) {


                Holder newFirst = new Holder(null, last.getProduct(), null);
                Holder newLast = new Holder(newFirst, first.getProduct(), null);
                newFirst.setNextHolder(newLast);

                first = newFirst;
                last = newLast;
                System.out.println(last);
                System.out.println("last prev "+ last.getPreviousHolder());

                return;
            }
            else if (size == 0) {
                return;
            }
            else{
                        first = last;
        Holder current = last.getPreviousHolder();
        addLast(current.getProduct());
        

        current = current.getPreviousHolder();
        while(current.getPreviousHolder() != null){

            addLast(current.getProduct());
            current = current.getPreviousHolder();
        }
        addLast(current.getProduct());
        size = (size / 2) + 1;


        }
        }
    }

    public String print() {
        if (size == 0) {
            return "{}";
        } else {
            if (first != null) {

                String MyHolder = "";
                Holder current = first;
                 System.out.print("Holder Line : ");
                MyHolder += "{";

                while (current.getNextHolder() != null) {
                     System.out.print(current);
                     System.out.print(" ");
                    MyHolder += current.getProduct() + ",";
                    current = current.getNextHolder();
                }
                 System.out.println(current);
                MyHolder += current.getProduct();
                MyHolder += "}";

                System.out.println("Size of the Holder: " + size);
                System.out.println("Head is: " + first + " and tail is " + last);
                 System.out.println();
                return MyHolder;
            } else {

                String MyHolder = "";
                Holder current = last;
                MyHolder += "{";

                while (current.getPreviousHolder() != null) {

                    MyHolder = current.getProduct() + "," + MyHolder;
                    current = current.getPreviousHolder();
                }
                MyHolder += current.getProduct();
                MyHolder += "}";

                System.out.println("Size of the Holder: " + size);
                System.out.println("Head is: " + first + " and tail is " + last);
                return MyHolder;
            }
        }

    }
}
