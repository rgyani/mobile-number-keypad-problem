// Count number of possible numbers of given length on a Phone Keypad
class Solution
{

    // Return count of all possible numbers of length n in a given numeric keyboard
    static int getCountWithAuxilarySpace(char[][] keypad, int N)
    {
        //boundary checks
        if (keypad == null || N <= 0)
            return 0;
        if (N == 1)
            return 10;

        // left, up, right, down move from current location
        int[] row = {0, 0, -1, 0, 1};
        int[] col = {0, -1, 0, 1, 0};

        // taking n+1 for simplicity - count[i][j] will store
        // number count starting with digit i and length j
        int[][] count = new int[10][N + 1];

        // count numbers starting with digit i
        // and of lengths 0 and 1
        for (int i = 0; i <= 9; i++)
        {
            count[i][0] = 0;
            count[i][1] = 1;
        }

        // Bottom up - Get number count of length 2, 3, 4, ... , n
        for (int k = 2; k <= N; k++)
        {
            for (int i = 0; i < 4; i++) // Loop on keypad row
            {
                for (int j = 0; j < 3; j++) // Loop on keypad column
                {
                    // Process for 0 to 9 digits
                    if (keypad[i][j] != '*' && keypad[i][j] != '#')
                    {
                        // Here we are counting the numbers starting with
                        // digit keypad[i][j] and of length k keypad[i][j]
                        // will become 1st digit, and we need to look for
                        // (k-1) more digits
                        int num = keypad[i][j] - '0';
                        count[num][k] = 0;

                        // move left, up, right, down from current location
                        // and if new location is valid, then get number
                        // count of length (k-1) from that new digit and
                        // add in count we found so far
                        for (int move = 1; move < 5; move++)
                        {
                            int r = i + row[move];
                            int c = j + col[move];
                            if (r >= 0 && r <= 3 && c >= 0 && c <= 2
                                    && keypad[r][c] != '*' && keypad[r][c] != '#')
                            {
                                int nextNum = keypad[r][c] - '0';
                                count[num][k] += count[nextNum][k - 1];
                            }
                        }
                    }
                }
            }
        }

        // Get count of all possible numbers of length "n"
        // starting with digit 0, 1, 2, ..., 9
        int totalCount = 0;
        for (int i = 0; i <= 9; i++)
            totalCount += count[i][N];
        return totalCount;
    }

    // Return count of all possible numbers of length n in a given numeric keyboard
    static int getCountWithFixedSpace(char[][] keypad, int N)
    {
        //boundary checks
        if (keypad == null || N <= 0)
            return 0;
        if (N == 1)
            return 10;

        // odd[i], even[i] arrays represent count of numbers starting
        // with digit i for any length j
        int[] odd = new int[10];
        int[] even = new int[10];

        // for N = 1
        for (int i = 0; i <= 9; i++)
            even[i] = 1;

        // Bottom Up calculation from j = 2 to n
        for (int j = 2; j <= N; j++)
        {
            boolean isEven = (j % 2 == 0);

            // Here we are explicitly writing lines for each number 0
            // to 9. But it can always be written as DFS on 4X3 grid
            // using row, column array valid moves
            if (isEven)
            {
                odd[0] = even[8];
                odd[1] = even[2] + even[4];
                odd[2] = even[1] + even[3] + even[5];
                odd[3] = even[2] + even[6];
                odd[4] = even[1] + even[5] + even[7];
                odd[5] = even[2] + even[4] + even[8] + even[6];
                odd[6] = even[3] + even[5] + even[9];
                odd[7] = even[4] + even[8];
                odd[8] = even[0] + even[5] + even[7] + even[9];
                odd[9] = even[6] + even[8];
            }
            else
            {
                even[0] = odd[8];
                even[1] = odd[2] + odd[4];
                even[2] = odd[1] + odd[3] + odd[5];
                even[3] = odd[2] + odd[6];
                even[4] = odd[1] + odd[5] + odd[7];
                even[5] = odd[2] + odd[4] + odd[8] + odd[6];
                even[6] = odd[3] + odd[5] + odd[9];
                even[7] = odd[4] + odd[8];
                even[8] = odd[0] + odd[5] + odd[7] + odd[9];
                even[9] = odd[6] + odd[8];
            }
        }

        // Get count of all possible numbers of length "n" starting
        // with digit 0, 1, 2, ..., 9
        int totalCount = 0;
        for (int i = 0; i <= 9; i++)
            totalCount += (N % 2 == 0) ? odd[i] : even[i];
        return totalCount;
    }

    // Driver Code
    public static void main(String[] args)
    {
        char[][] keypad = {{'1', '2', '3'},
                {'4', '5', '6'},
                {'7', '8', '9'},
                {'*', '0', '#'}};

        for (int N = 1; N <= 9; N++)
            System.out.printf("Count for numbers of length %d: %d\n", N,
                    getCountWithAuxilarySpace(keypad, N));

        for (int N = 1; N <= 9; N++)
            System.out.printf("Count for numbers of length %d: %d\n", N,
                    getCountWithFixedSpace(keypad, N));

    }
}
